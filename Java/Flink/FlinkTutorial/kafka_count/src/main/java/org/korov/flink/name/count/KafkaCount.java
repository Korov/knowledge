package org.korov.flink.name.count;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
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
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.korov.flink.common.deserialization.KeyAlertDeserializer;
import org.korov.flink.common.enums.SinkType;
import org.korov.flink.common.model.NameModel;
import org.korov.flink.common.sink.KeyAlertMongoSink;

import java.time.Duration;

/**
 * @author zhu.lei
 * @date 2021-05-05 14:00
 */
@Slf4j
public class KafkaCount {
    // private static final String MONGO_HOST = "localhost";
    private static final String MONGO_HOST = "korov.myqnapcloud.cn";
    private static final String DB_NAME = "kafka";

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);
        env.setParallelism(2);
        env.getConfig().setUseSnapshotCompression(true);
        env.getCheckpointConfig().setCheckpointInterval(5 * 60000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(60000);
        env.getCheckpointConfig().setCheckpointTimeout(5 * 60000);
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        env.getCheckpointConfig().enableExternalizedCheckpoints(
                CheckpointConfig.ExternalizedCheckpointCleanup.DELETE_ON_CANCELLATION);

        EmbeddedRocksDBStateBackend rocksDbStateBackend = new EmbeddedRocksDBStateBackend(true);
        rocksDbStateBackend.setDbStoragePath("file:////opt/flink/rocksdb");
        env.setStateBackend(rocksDbStateBackend);
        env.getCheckpointConfig().setCheckpointStorage("s3://flink/checkpoints");
        env.enableCheckpointing(10000, CheckpointingMode.EXACTLY_ONCE);

        KafkaSource<Tuple3<String, NameModel, Long>> kafkaSource = KafkaSource.<Tuple3<String, NameModel, Long>>builder()
                .setBootstrapServers("192.168.1.19:9092")
                .setGroupId("kafka-name-count")
                .setStartingOffsets(OffsetsInitializer.committedOffsets(OffsetResetStrategy.EARLIEST))
                .setTopics("flink_siem")
                .setDeserializer(new KeyAlertDeserializer())
                .build();

        DataStream<Tuple3<String, NameModel, Long>> stream = env.fromSource(kafkaSource, WatermarkStrategy.<Tuple3<String, NameModel, Long>>forBoundedOutOfOrderness(Duration.ofMinutes(5))
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
                }), "kafka-source");

        KeyAlertMongoSink mongoNameSink = new KeyAlertMongoSink(MONGO_HOST, 27017, DB_NAME, "kafka-name-count", SinkType.KEY_NAME);
        stream.keyBy(new KeySelector<Tuple3<String, NameModel, Long>, Object>() {
                    @Override
                    public Object getKey(Tuple3<String, NameModel, Long> value) throws Exception {
                        try {
                            return Joiner.on("-").join(value.f0, value.f1.getName());
                        } catch (Exception e) {
                            log.error("get name key failed", e);
                            return value.f0 + "null";
                        }
                    }
                }).window(TumblingProcessingTimeWindows.of(Time.minutes(1)))
                .reduce(new ReduceFunction<Tuple3<String, NameModel, Long>>() {
                    @Override
                    public Tuple3<String, NameModel, Long> reduce(Tuple3<String, NameModel, Long> value1, Tuple3<String, NameModel, Long> value2) throws Exception {
                        return new Tuple3<>(value1.f0, value1.f1, value1.f2 + value2.f2);
                    }
                })
                .addSink(mongoNameSink).name("mongo-name-sink");

        KeyAlertMongoSink mongoKeySink = new KeyAlertMongoSink(MONGO_HOST, 27017, DB_NAME, "kafka-key-count", SinkType.KEY);
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
                }).window(TumblingProcessingTimeWindows.of(Time.minutes(1)))
                .reduce(new ReduceFunction<Tuple3<String, NameModel, Long>>() {
                    @Override
                    public Tuple3<String, NameModel, Long> reduce(Tuple3<String, NameModel, Long> value1, Tuple3<String, NameModel, Long> value2) throws Exception {
                        return new Tuple3<>(value1.f0, value1.f1, value1.f2 + value2.f2);
                    }
                })
                .addSink(mongoKeySink).name("mongo-key-sink");

        KeyAlertMongoSink mongoValueSink = new KeyAlertMongoSink(MONGO_HOST, 27017, DB_NAME, "value-record", SinkType.KEY_NAME_VALUE);
        stream.addSink(mongoValueSink).name("mongo-value-sink");
        env.execute("kafka-count");
    }
}
