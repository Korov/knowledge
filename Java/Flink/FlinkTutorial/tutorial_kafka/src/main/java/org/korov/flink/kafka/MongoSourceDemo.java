package org.korov.flink.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.korov.flink.common.model.AlertModel;
import org.korov.flink.common.sink.MongoSink;
import org.korov.flink.common.source.MongoSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

        DataStream<KeyValue> keyValueDataStream = stream.flatMap(new FlatMapFunction<Tuple3<String, String, Long>, KeyValue>() {
            @Override
            public void flatMap(Tuple3<String, String, Long> value, Collector<KeyValue> out) throws Exception {
                KeyValue keyValue = new KeyValue();
                keyValue.setKey(value.f0);
                keyValue.setCount(1L);

                ObjectMapper mapper = new ObjectMapper();
                AlertModel alert = null;
                try {
                    alert = mapper.readValue(value.f1, AlertModel.class);
                } catch (JsonProcessingException e) {
                    keyValue.setTimestamp(System.currentTimeMillis());
                }
                if (alert == null || alert.getMetaModel() == null || alert.getMetaModel().getTimestamp() == null){
                    keyValue.setTimestamp(System.currentTimeMillis());
                } else {
                    keyValue.setTimestamp(alert.getMetaModel().getTimestamp());
                }

                out.collect(keyValue);
            }
        });

        keyValueDataStream.assignTimestampsAndWatermarks(WatermarkStrategy.<KeyValue>forBoundedOutOfOrderness(Duration.ofMinutes(1))
                .withTimestampAssigner(new SerializableTimestampAssigner<KeyValue>() {
                    @Override
                    public long extractTimestamp(KeyValue element, long recordTimestamp) {
                        return element.getTimestamp();
                    }
                }))
                .keyBy(new KeySelector<KeyValue, Object>() {
                    @Override
                    public Object getKey(KeyValue value) throws Exception {
                        return value.getKey();
                    }
                })
                .window(TumblingProcessingTimeWindows.of(Time.minutes(1), Time.seconds(0)))
                .reduce(new ReduceFunction<KeyValue>() {
                    @Override
                    public KeyValue reduce(KeyValue value1, KeyValue value2) throws Exception {
                        KeyValue keyValue = new KeyValue();
                        keyValue.setKey(value2.key);
                        keyValue.setTimestamp(value2.timestamp);
                        keyValue.setCount(value1.count + value2.count);
                        return keyValue;
                    }
                });
        keyValueDataStream.print();
        env.execute("mongo-to-mongo");
    }

    @Data
    public static class KeyValue {
        private String key;
        private Long timestamp;
        private Long count;
    }
}
