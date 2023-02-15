package org.korov.flink.name.count;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.contrib.streaming.state.EmbeddedRocksDBStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.korov.flink.name.count.deserialization.KeyAlertDeserializer;
import org.korov.flink.name.count.model.NameModel;
import org.korov.flink.name.count.serialization.KeyAlertSerialization;

import java.time.Duration;

/**
 * 将kafka中的数据格式化之后发送到kafka中
 * org.korov.flink.name.count.UnionStream
 * <p>
 * --sink_addr 192.168.50.100:9092 --sink_topic sink_union --kafka_addr 192.168.1.19:9092 --kafka_topic flink_siem --kafka_group kafka_union_sink
 *
 * @author zhu.lei
 * @date 2021-05-05 14:00
 */
@Slf4j
public class UnionStream {
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

        KafkaSource<Tuple3<String, NameModel, Long>> kafkaSource1 = KafkaSource.<Tuple3<String, NameModel, Long>>builder()
                .setBootstrapServers(kafkaAddr)
                .setGroupId(kafkaGroup)
                .setStartingOffsets(OffsetsInitializer.committedOffsets(OffsetResetStrategy.EARLIEST))
                .setTopics(kafkaTopic)
                .setDeserializer(new KeyAlertDeserializer())
                .build();

        KafkaSource<Tuple3<String, NameModel, Long>> kafkaSource2 = KafkaSource.<Tuple3<String, NameModel, Long>>builder()
                .setBootstrapServers(kafkaAddr)
                .setGroupId(kafkaGroup)
                .setStartingOffsets(OffsetsInitializer.committedOffsets(OffsetResetStrategy.EARLIEST))
                .setTopics(kafkaTopic)
                .setDeserializer(new KeyAlertDeserializer())
                .build();

        KafkaSink<Tuple3<String, NameModel, Long>> kafkaSink = KafkaSink.<Tuple3<String, NameModel, Long>>builder()
                .setBootstrapServers(sinkAddr)
                .setRecordSerializer(new KeyAlertSerialization(sinkTopic))
                .setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .build();

        DataStream<Tuple3<String, NameModel, Long>> stream1 = env.fromSource(kafkaSource1,
                WatermarkStrategy.<Tuple3<String, NameModel, Long>>forBoundedOutOfOrderness(Duration.ofMinutes(5))
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
                        }).withIdleness(Duration.ofMinutes(5)), "kafka-source1");

        DataStream<Tuple3<String, NameModel, Long>> stream2 = env.fromSource(kafkaSource2,
                WatermarkStrategy.<Tuple3<String, NameModel, Long>>forBoundedOutOfOrderness(Duration.ofMinutes(5))
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
                        }).withIdleness(Duration.ofMinutes(5)), "kafka-source2");
        // 将两个stream进行合并
        DataStream<Tuple3<String, NameModel, Long>> unionStream = stream1.union(stream2);

        OutputTag<Tuple3<String, NameModel, Long>> outputTag1 = new OutputTag<Tuple3<String, NameModel, Long>>("union_tag1", TypeInformation.of(new TypeHint<Tuple3<String, NameModel, Long>>() {
        }));
        OutputTag<Tuple3<String, NameModel, Long>> outputTag2 = new OutputTag<Tuple3<String, NameModel, Long>>("union_tag2", TypeInformation.of(new TypeHint<Tuple3<String, NameModel, Long>>() {
        }));
        SingleOutputStreamOperator<Tuple3<String, NameModel, Long>> outputStreamOperator = unionStream.process(new ProcessFunction<Tuple3<String, NameModel, Long>, Tuple3<String, NameModel, Long>>() {
            @Override
            public void processElement(Tuple3<String, NameModel, Long> value, ProcessFunction<Tuple3<String, NameModel, Long>, Tuple3<String, NameModel, Long>>.Context ctx, Collector<Tuple3<String, NameModel, Long>> out) throws Exception {
                out.collect(value);
                // 将数据发送到侧输出中
                ctx.output(outputTag1, value);
                ctx.output(outputTag2, value);
            }
        }).name("op_union");

        DataStream<Tuple3<String, NameModel, Long>> op1 = outputStreamOperator.getSideOutput(outputTag1)
                .filter(new FilterFunction<Tuple3<String, NameModel, Long>>() {
                    @Override
                    public boolean filter(Tuple3<String, NameModel, Long> value) throws Exception {
                        if (value == null || value.f0 == null || value.f1 == null || value.f2 == null) {
                            return false;
                        }
                        return true;
                    }
                }).keyBy(new KeySelector<Tuple3<String, NameModel, Long>, Object>() {
                    @Override
                    public Object getKey(Tuple3<String, NameModel, Long> value) throws Exception {
                        try {
                            return value.f0;
                        } catch (Exception e) {
                            log.error("get key failed", e);
                            return "null";
                        }
                    }
                }).window(TumblingEventTimeWindows.of(Time.minutes(10)))
                .reduce(new ReduceFunction<Tuple3<String, NameModel, Long>>() {
                    @Override
                    public Tuple3<String, NameModel, Long> reduce(Tuple3<String, NameModel, Long> value1, Tuple3<String, NameModel, Long> value2) throws Exception {
                        NameModel nameModel = value1.f1;
                        long minTime = Math.min(value1.f1.getTimestamp(), value2.f1.getTimestamp());
                        long maxTime = Math.max(value1.f1.getTimestamp(), value2.f1.getTimestamp());
                        nameModel.setMinTime(Math.min(nameModel.getMinTime(), minTime));
                        nameModel.setMaxTime(Math.max(nameModel.getMaxTime(), maxTime));
                        return new Tuple3<>(value1.f0, value1.f1, value1.f2 + value2.f2);
                    }
                }).name("op1");

        DataStream<Tuple3<String, NameModel, Long>> op2 = outputStreamOperator.getSideOutput(outputTag2)
                .filter(new FilterFunction<Tuple3<String, NameModel, Long>>() {
                    @Override
                    public boolean filter(Tuple3<String, NameModel, Long> value) throws Exception {
                        if (value == null || value.f0 == null || value.f1 == null || value.f2 == null) {
                            return false;
                        }
                        return true;
                    }
                }).keyBy(new KeySelector<Tuple3<String, NameModel, Long>, Object>() {
                    @Override
                    public Object getKey(Tuple3<String, NameModel, Long> value) throws Exception {
                        try {
                            return value.f0;
                        } catch (Exception e) {
                            log.error("get key failed", e);
                            return "null";
                        }
                    }
                }).window(TumblingEventTimeWindows.of(Time.minutes(10)))
                .reduce(new ReduceFunction<Tuple3<String, NameModel, Long>>() {
                    @Override
                    public Tuple3<String, NameModel, Long> reduce(Tuple3<String, NameModel, Long> value1, Tuple3<String, NameModel, Long> value2) throws Exception {
                        NameModel nameModel = value1.f1;
                        long minTime = Math.min(value1.f1.getTimestamp(), value2.f1.getTimestamp());
                        long maxTime = Math.max(value1.f1.getTimestamp(), value2.f1.getTimestamp());
                        nameModel.setMinTime(Math.min(nameModel.getMinTime(), minTime));
                        nameModel.setMaxTime(Math.max(nameModel.getMaxTime(), maxTime));
                        return new Tuple3<>(value1.f0, value1.f1, value1.f2 + value2.f2);
                    }
                }).name("op2");

        DataStream<Tuple3<String, NameModel, Long>> allOP = op1.union(op2);

        DataStream<Tuple3<String, NameModel, Long>> filterStream = allOP.filter(new FilterFunction<Tuple3<String, NameModel, Long>>() {
            @Override
            public boolean filter(Tuple3<String, NameModel, Long> value) throws Exception {
                if (value == null || value.f0 == null || value.f1 == null || value.f2 == null) {
                    return false;
                }
                return true;
            }
        }).name("all_filter");

        DataStream<Tuple3<String, NameModel, Long>> sinkStream = unionStream.union(filterStream);
        sinkStream.sinkTo(kafkaSink).name("sink");
        env.execute("UnionStream");
    }
}
