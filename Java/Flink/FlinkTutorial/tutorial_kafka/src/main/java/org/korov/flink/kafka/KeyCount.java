package org.korov.flink.kafka;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.contrib.streaming.state.RocksDBStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.korov.flink.common.deserialization.KeyValueDeserializer;
import org.korov.flink.common.sink.MongoSink;

import java.util.Properties;

public class KeyCount {
    /**
     * 0:kafka server
     * 1:mongo host
     * 2:mongo port
     * 3:checkpoint path
     *
     * example  korov-linux.org:9092 korov-linux.org 27017 file:///home/korov/Desktop/temp/flink
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args == null || args.length != 4) {
            args = new String[4];
            args[0] = "korov-linux.org:9092";
            args[1] = "korov-linux.org";
            args[2] = "27017";
            args[3] = "file:///home/korov/Desktop/temp/flink";
        }

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);

        env.getConfig().setUseSnapshotCompression(true);
        env.getCheckpointConfig().setCheckpointInterval(5 * 60000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(60000);
        env.getCheckpointConfig().setCheckpointTimeout(5 * 60000);
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        env.getCheckpointConfig().enableExternalizedCheckpoints(
                CheckpointConfig.ExternalizedCheckpointCleanup.DELETE_ON_CANCELLATION);

        RocksDBStateBackend rocksDbStateBackend = new RocksDBStateBackend(args[3], true);
        env.setStateBackend(rocksDbStateBackend);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", args[0]);
        properties.setProperty("group.id", "flink_test");
        KeyValueDeserializer serializationSchema = new KeyValueDeserializer();
        FlinkKafkaConsumer<Tuple3<String, String, Long>> consumer = new FlinkKafkaConsumer<Tuple3<String, String, Long>>("test", serializationSchema, properties);
        consumer.setStartFromLatest();
        consumer.setCommitOffsetsOnCheckpoints(true);

        DataStream<Tuple3<String, String, Long>> stream = env.addSource(consumer, "kafka-source");
        MongoSink mongoSink = new MongoSink(args[1], Integer.parseInt(args[2]), "admin", "alert");
        stream.addSink(mongoSink).name("mongo-sink");
        env.execute("kafka-to-mongo");
    }
}
