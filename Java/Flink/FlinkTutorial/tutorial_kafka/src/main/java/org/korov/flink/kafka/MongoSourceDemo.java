package org.korov.flink.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.korov.flink.common.model.AlertModel;
import org.korov.flink.common.sink.MongoSink;
import org.korov.flink.common.source.MongoSource;

import java.time.Duration;

/**
 * @author zhu.lei
 * @date 2021-05-05 14:00
 */
public class MongoSourceDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);
        env.setParallelism(2);

        MongoSource mongoSource = new MongoSource("localhost", 27017, "admin", "alert");

        DataStream<Tuple3<String, String, Long>> stream = env.addSource(mongoSource, "mongo-source");
        MongoSink mongoSink = new MongoSink("localhost", 27017, "admin", "alert-test1");
        stream.addSink(mongoSink).name("mongo-sink");
        stream.assignTimestampsAndWatermarks(WatermarkStrategy.<Tuple3<String, String, Long>>forBoundedOutOfOrderness(Duration.ofMinutes(1))
                .withTimestampAssigner(new SerializableTimestampAssigner<Tuple3<String, String, Long>>() {
                    @Override
                    public long extractTimestamp(Tuple3<String, String, Long> element, long recordTimestamp) {
                        ObjectMapper mapper = new ObjectMapper();
                        AlertModel alert;
                        try {
                            alert = mapper.readValue(element.f1, AlertModel.class);
                        } catch (JsonProcessingException e) {
                            return System.currentTimeMillis();
                        }
                        return System.currentTimeMillis();
                    }
                }))
                .keyBy(new KeySelector<Tuple3<String, String, Long>, Object>() {
                    @Override
                    public Object getKey(Tuple3<String, String, Long> value) throws Exception {
                        return value.f0;
                    }
                })
                .window(TumblingProcessingTimeWindows.of(Time.minutes(1), Time.seconds(0)))
                .sum(2);
        stream.print();
        env.execute("mongo-to-mongo");
    }
}
