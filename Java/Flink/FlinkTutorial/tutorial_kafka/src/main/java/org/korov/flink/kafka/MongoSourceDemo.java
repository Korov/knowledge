package org.korov.flink.kafka;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.contrib.streaming.state.RocksDBStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.korov.flink.common.sink.MongoSink;
import org.korov.flink.common.source.MongoSource;

/**
 * @author zhu.lei
 * @date 2021-05-05 14:00
 */
public class MongoSourceDemo {
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

        RocksDBStateBackend rocksDbStateBackend = new RocksDBStateBackend(args[3], true);
        env.setStateBackend(rocksDbStateBackend);

        MongoSource mongoSource = new MongoSource("localhost", 27017, "admin", "alert-test");

        DataStream<Tuple3<String, String, Long>> stream = env.addSource(mongoSource, "mongo-source");
        MongoSink mongoSink = new MongoSink("localhost", 27017, "admin", "alert-test1");
        stream.addSink(mongoSink).name("mongo-sink");
        env.execute("mongo-to-mongo");
    }
}
