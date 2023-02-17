package org.korov.flink.name.count;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.contrib.streaming.state.EmbeddedRocksDBStateBackend;
import org.apache.flink.metrics.Gauge;
import org.apache.flink.metrics.MetricGroup;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.korov.flink.common.utils.JSONUtils;
import org.korov.flink.name.count.deserialization.KeyValueDeserializer;
import org.korov.flink.name.count.model.NameModel;
import org.korov.flink.name.count.serialization.KeyAlertSerialization;
import org.korov.flink.name.count.serialization.KeyValueSerialization;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * 将kafka中的数据格式化之后发送到kafka中
 * org.korov.flink.name.count.KafkaToKafkaForHiSec
 * <p>
 * --sink_addr 192.168.50.27:9092 --sink_topic logriver_siem --kafka_addr 192.168.1.19:9092 --kafka_topic logriver_siem --kafka_group kafka_sink_hisec
 *
 * @author zhu.lei
 * @date 2021-05-05 14:00
 */
@Slf4j
public class KafkaToKafkaForHiSec {

    public static void main(String[] args) throws Exception {

        Options options = new Options();
        options.addOption(Option.builder().longOpt("sink_addr").hasArg(true).required(true).build());
        options.addOption(Option.builder().longOpt("sink_topic").hasArg(true).required(true).build());

        options.addOption(Option.builder().longOpt("kafka_addr").hasArg(true).required(true).build());
        options.addOption(Option.builder().longOpt("kafka_topic").hasArg(true).required(true).build());
        options.addOption(Option.builder().longOpt("kafka_group").hasArg(true).required(true).build());

        CommandLine cmd = new DefaultParser().parse(options, args);
        String sinkAddr = cmd.getOptionValue("sink_addr");
        String sinkTopic = cmd.getOptionValue("sink_topic");

        String kafkaAddr = cmd.getOptionValue("kafka_addr");
        String kafkaTopic = cmd.getOptionValue("kafka_topic");
        String kafkaGroup = cmd.getOptionValue("kafka_group");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);
        env.getConfig().setUseSnapshotCompression(true);
        env.getCheckpointConfig().setCheckpointInterval(5 * 60000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(60000);
        env.getCheckpointConfig().setCheckpointTimeout(5 * 60000);
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        env.getCheckpointConfig().setExternalizedCheckpointCleanup(
                CheckpointConfig.ExternalizedCheckpointCleanup.DELETE_ON_CANCELLATION);

        EmbeddedRocksDBStateBackend rocksDbStateBackend = new EmbeddedRocksDBStateBackend(true);
        rocksDbStateBackend.setDbStoragePath("file:////opt/flink/rocksdb");
        env.setStateBackend(rocksDbStateBackend);
        env.getCheckpointConfig().setCheckpointStorage("file:////opt/flink/savepoints");
        env.enableCheckpointing(10000, CheckpointingMode.EXACTLY_ONCE);

        KafkaSource<Tuple2<String, String>> kafkaSource = KafkaSource.<Tuple2<String, String>>builder()
                .setBootstrapServers(kafkaAddr)
                .setGroupId(kafkaGroup)
                .setStartingOffsets(OffsetsInitializer.committedOffsets(OffsetResetStrategy.EARLIEST))
                .setTopics(kafkaTopic)
                .setDeserializer(new KeyValueDeserializer())
                .build();

        KafkaSink<Tuple2<String, String>> kafkaSink = KafkaSink.<Tuple2<String, String>>builder()
                .setBootstrapServers(sinkAddr)
                .setRecordSerializer(new KeyValueSerialization(sinkTopic))
                .setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .build();

        RichFilterFunction<Tuple2<String, String>> hiSecFilter = new RichFilterFunction<Tuple2<String, String>>() {

            private transient long ignoreCount = 0L;
            private transient long validCount = 0L;

            @Override
            public void open(Configuration parameters) throws Exception {
                super.open(parameters);
                MetricGroup metricGroup = getRuntimeContext().getMetricGroup();
                metricGroup.gauge("ignoreCount", (Gauge<Long>)() -> ignoreCount);
                metricGroup.gauge("validCount", (Gauge<Long>)() -> validCount);
            }

            @Override
            public boolean filter(Tuple2<String, String> value) throws Exception {
                if (value == null || value.f1 == null ) {
                    ignoreCount++;
                    return false;
                }
                String message = value.f1;
                Map<String, String> messageMap = Optional.ofNullable(JSONUtils.jsonToMapNoException(message)).orElse(Collections.emptyMap());
                if ("hisec".equalsIgnoreCase(messageMap.get("appname"))) {
                    validCount++;
                    return true;
                }
                ignoreCount++;
                return false;
            }
        };

        DataStream<Tuple2<String, String>> stream = env.fromSource(kafkaSource,
                WatermarkStrategy.<Tuple2<String, String>>forBoundedOutOfOrderness(Duration.ofMinutes(5))
                        .withTimestampAssigner(new SerializableTimestampAssigner<Tuple2<String, String>>() {
                            @Override
                            public long extractTimestamp(Tuple2<String, String> element, long recordTimestamp) {
                                try {
                                    return System.currentTimeMillis();
                                } catch (Exception e) {
                                    log.error("get name key timestamp failed", e);
                                    return System.currentTimeMillis();
                                }
                            }
                        }).withIdleness(Duration.ofMinutes(5)), "kafka-source")
                .filter(hiSecFilter).name("hiSecFilter");
        stream.sinkTo(kafkaSink);
        env.execute(String.format("hisec kafka:[%s,%s,%s] to kafka:[%s,%s]", kafkaAddr,kafkaTopic, kafkaGroup, sinkAddr, sinkTopic));
    }
}
