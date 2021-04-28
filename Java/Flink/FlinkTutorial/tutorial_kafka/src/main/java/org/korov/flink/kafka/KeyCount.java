package org.korov.flink.kafka;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.contrib.streaming.state.RocksDBStateBackend;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.util.serialization.TypeInformationKeyValueSerializationSchema;
import org.korov.flink.deserialization.KeyValueDeserializer;
import org.korov.flink.sink.MongoSink;

import java.util.Properties;

public class KeyCount {
    /**
     * 0:kafka server
     * 1:mongo host
     * 2:mongo port
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
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

        RocksDBStateBackend rocksDBStateBackend = new RocksDBStateBackend("/tmp/flink", true);
        env.setStateBackend(rocksDBStateBackend);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", args[0]);
        properties.setProperty("group.id", "flink_test");
        KeyValueDeserializer serializationSchema = new KeyValueDeserializer();
        FlinkKafkaConsumer<Tuple3<String, String, Long>> consumer = new FlinkKafkaConsumer<Tuple3<String, String, Long>>("test", serializationSchema, properties);
        consumer.setStartFromLatest();
        consumer.setCommitOffsetsOnCheckpoints(true);

        DataStream<Tuple3<String, String, Long>> stream = env.addSource(consumer);
        MongoSink mongoSink = new MongoSink(args[1], Integer.parseInt(args[2]));
        stream.addSink(mongoSink);
        env.execute();
    }
}
