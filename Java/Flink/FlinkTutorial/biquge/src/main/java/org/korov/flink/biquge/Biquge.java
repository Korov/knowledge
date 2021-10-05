package org.korov.flink.biquge;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.bson.Document;
import org.korov.flink.common.model.NameModel;
import org.korov.flink.common.source.MongoSource;

import java.time.Duration;

@Slf4j
public class Biquge {
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

        MongoSource mongoSource = new MongoSource("192.168.50.189", 27017, "kafka", "value-record");

        DataStream<Tuple3<String, String ,Long>> stream = env.addSource(mongoSource, "mongo-source", null);
        DataStream<Tuple3<String, String ,Long>> outputStreamOperator = stream.flatMap(new FlatMapFunction<Tuple3<String, String ,Long>, Tuple3<String, String ,Long>>() {
            @Override
            public void flatMap(Tuple3<String, String ,Long> value, Collector<Tuple3<String, String ,Long>> out) throws Exception {
                out.collect(value);
            }
        });
        outputStreamOperator.print();
        env.execute();
    }
}
