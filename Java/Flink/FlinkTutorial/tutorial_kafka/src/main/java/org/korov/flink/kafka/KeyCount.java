package org.korov.flink.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.contrib.streaming.state.EmbeddedRocksDBStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.korov.flink.common.deserialization.KeyValueDeserializer;
import org.korov.flink.common.sink.MongoSink;

import java.time.Duration;

@Slf4j
public class KeyCount {
    /**
     * 0:kafka server
     * 1:mongo host
     * 2:mongo port
     * 3:checkpoint path
     * <p>
     * example  korov-linux.org:9092 korov-linux.org 27017 file:///home/korov/Desktop/temp/flink
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args == null || args.length != 3) {
            args = new String[3];
            args[0] = "korov-linux.org:9092";
            args[1] = "korov-linux.org";
            args[2] = "27017";
        }

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
        env.setStateBackend(rocksDbStateBackend);

        KafkaSource<Tuple3<String, String, Long>> kafkaSource = KafkaSource.<Tuple3<String, String, Long>>builder()
                .setBootstrapServers(args[0])
                .setGroupId("flink_test")
                .setStartingOffsets(OffsetsInitializer.committedOffsets(OffsetResetStrategy.LATEST))
                .setTopics("test")
                .setDeserializer(new KeyValueDeserializer())
                .build();


        DataStream<Tuple3<String, String, Long>> stream = env.fromSource(kafkaSource, WatermarkStrategy.<Tuple3<String, String, Long>>forBoundedOutOfOrderness(Duration.ofMinutes(5))
                .withTimestampAssigner(new SerializableTimestampAssigner<Tuple3<String, String, Long>>() {
                    @Override
                    public long extractTimestamp(Tuple3<String, String, Long> element, long recordTimestamp) {
                        log.info("key:{}, value:{}, count:{}", element.f0, element.f1, element.f2);
                        return Long.parseLong(element.f1);
                    }
                }), "kafka-source");
        MongoSink mongoSink = new MongoSink(args[1], Integer.parseInt(args[2]), "admin", "alert");
        stream.addSink(mongoSink).name("mongo-sink");
        env.execute("kafka-to-mongo");
    }
}
