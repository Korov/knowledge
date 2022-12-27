package org.korov.flink.name.count;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.contrib.streaming.state.EmbeddedRocksDBStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.korov.flink.name.count.deserialization.KeyAlertDeserializer;
import org.korov.flink.name.count.enums.SinkType;
import org.korov.flink.name.count.model.NameModel;
import org.korov.flink.name.count.sink.KeyAlertMongoSink;

import java.time.Duration;

/**
 * 将kafka中的数据格式化之后发送到mongo中
 * org.korov.flink.name.count.KafkaKeyWindowCount
 * <p>
 * --mongo_host 192.168.50.100 --mongo_port 27017 --mongo_db kafka --mongo_collection kafka_key_count --kafka_addr 192.168.1.19:9092 --kafka_topic flink_siem --kafka_group kafka-key-count
 *
 * @author zhu.lei
 * @date 2021-05-05 14:00
 */
@Slf4j
public class KafkaKeyWindowCount {

    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("mongo_host").hasArg(true).required(true).build());
        options.addOption(Option.builder().longOpt("mongo_port").hasArg(true).required(true).build());
        options.addOption(Option.builder().longOpt("mongo_db").hasArg(true).required(true).build());
        options.addOption(Option.builder().longOpt("mongo_collection").hasArg(true).required(true).build());

        options.addOption(Option.builder().longOpt("kafka_addr").hasArg(true).required(true).build());
        options.addOption(Option.builder().longOpt("kafka_topic").hasArg(true).required(true).build());
        options.addOption(Option.builder().longOpt("kafka_group").hasArg(true).required(true).build());

        CommandLine cmd = new DefaultParser().parse(options, args);
        String mongoHost = cmd.getOptionValue("mongo_host");
        int mongoPort = Integer.parseInt(cmd.getOptionValue("mongo_port"));
        String mongoUser = cmd.getOptionValue("mongo_user");
        String mongoPassword = cmd.getOptionValue("mongo_password");
        String mongoDb = cmd.getOptionValue("mongo_db");
        String mongoCollection = cmd.getOptionValue("mongo_collection");

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
        env.enableCheckpointing(600000, CheckpointingMode.EXACTLY_ONCE);

        KafkaSource<Tuple3<String, NameModel, Long>> kafkaSource = KafkaSource.<Tuple3<String, NameModel, Long>>builder()
                .setBootstrapServers(kafkaAddr)
                .setGroupId(kafkaGroup)
                .setStartingOffsets(OffsetsInitializer.committedOffsets(OffsetResetStrategy.EARLIEST))
                .setTopics(kafkaTopic)
                .setDeserializer(new KeyAlertDeserializer())
                .build();

        DataStream<Tuple3<String, NameModel, Long>> stream = env.fromSource(kafkaSource,
                // 允许数据迟到5分钟
                WatermarkStrategy.<Tuple3<String, NameModel, Long>>forBoundedOutOfOrderness(Duration.ofMinutes(5))
                        // 抽取kafka中的告警时间作为事件时间
                        .withTimestampAssigner(new SerializableTimestampAssigner<Tuple3<String, NameModel, Long>>() {
                            @Override
                            public long extractTimestamp(Tuple3<String, NameModel, Long> element, long recordTimestamp) {
                                try {
                                    return element.f1.getTimestamp();
                                } catch (Exception e) {
                                    log.error("get name key timestamp failed", e);
                                    return System.currentTimeMillis();
                                }
                            }
                        }).withIdleness(Duration.ofMinutes(5)), "kafka-source");

        KeyAlertMongoSink mongoKeySink = new KeyAlertMongoSink(mongoHost, mongoPort, mongoDb, mongoCollection, mongoUser, mongoPassword, SinkType.KEY_NAME);
        stream.keyBy(new KeySelector<Tuple3<String, NameModel, Long>, Object>() {
                    @Override
                    public Object getKey(Tuple3<String, NameModel, Long> value) throws Exception {
                        try {
                            return value.f0;
                        } catch (Exception e) {
                            log.error("get key failed", e);
                            return "null";
                        }
                    }
                })
                .window(TumblingEventTimeWindows.of(Time.minutes(10)))
                .reduce(new ReduceFunction<Tuple3<String, NameModel, Long>>() {
                    @Override
                    public Tuple3<String, NameModel, Long> reduce(Tuple3<String, NameModel, Long> value1, Tuple3<String, NameModel, Long> value2) throws Exception {
                        NameModel nameModel = value1.f1;
                        nameModel.setMinTime(Math.min(nameModel.getMinTime(), value2.f1.getMinTime()));
                        nameModel.setMaxTime(Math.max(nameModel.getMaxTime(), value2.f1.getMaxTime()));
                        return new Tuple3<>(value1.f0, value1.f1, value1.f2 + value2.f2);
                    }
                })
                .addSink(mongoKeySink).name("mongo-key-sink");

        env.execute("KafkaKeyWindowCount");
    }
}
