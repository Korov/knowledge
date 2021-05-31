package org.korov.flink.name.count;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.contrib.streaming.state.EmbeddedRocksDBStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.korov.flink.common.deserialization.KeyAlertDeserializer;
import org.korov.flink.common.model.NameModel;
import org.korov.flink.common.sink.KeyAlertMongoSink;

import java.time.Duration;
import java.util.Properties;

/**
 * @author zhu.lei
 * @date 2021-05-05 14:00
 */
@Slf4j
public class KafkaNameCount {
    // private static final String MONGO_HOST = "localhost";
    private static final String MONGO_HOST = "mongo-flink";

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
        env.setStateBackend(rocksDbStateBackend);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.1.19:9092");
        properties.setProperty("group.id", "kafka-name-count");
        properties.setProperty("auto.offset.reset", "earliest");
        KeyAlertDeserializer serializationSchema = new KeyAlertDeserializer();
        FlinkKafkaConsumer<Tuple3<String, NameModel, Long>> consumer = new FlinkKafkaConsumer<Tuple3<String, NameModel, Long>>("flink_siem", serializationSchema, properties);

        DataStream<Tuple3<String, NameModel, Long>> stream = env.addSource(consumer, "kafka-source");

        KeyAlertMongoSink mongoNameSink = new KeyAlertMongoSink(MONGO_HOST, 27017, "admin", "kafka-name-count");
        stream.assignTimestampsAndWatermarks(WatermarkStrategy.<Tuple3<String, NameModel, Long>>forBoundedOutOfOrderness(Duration.ofMinutes(5))
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
                })).keyBy(new KeySelector<Tuple3<String, NameModel, Long>, Object>() {
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

        KeyAlertMongoSink mongoKeySink = new KeyAlertMongoSink(MONGO_HOST, 27017, "admin", "kafka-key-count");
        stream.assignTimestampsAndWatermarks(WatermarkStrategy.<Tuple3<String, NameModel, Long>>forBoundedOutOfOrderness(Duration.ofMinutes(5))
                .withTimestampAssigner(new SerializableTimestampAssigner<Tuple3<String, NameModel, Long>>() {
                    @Override
                    public long extractTimestamp(Tuple3<String, NameModel, Long> element, long recordTimestamp) {
                        try {
                            return element.f1.getTimestamp();
                        } catch (Exception e) {
                            log.error("get key timestamp failed", e);
                            return System.currentTimeMillis();
                        }
                    }
                })).keyBy(new KeySelector<Tuple3<String, NameModel, Long>, Object>() {
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

        KeyAlertMongoSink mongoValueSink = new KeyAlertMongoSink(MONGO_HOST, 27017, "admin", "value-record");
        stream.addSink(mongoValueSink).name("mongo-name-sink");
        env.execute("kafka-name-count");
    }
}
