package org.korov.flink.key.count;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;
import org.korov.flink.common.deserialization.KeyValueDeserializer;
import org.korov.flink.common.model.AlertModel;
import org.korov.flink.common.model.KeyValue;
import org.korov.flink.common.sink.MongoSink;

import java.time.Duration;
import java.util.Properties;

/**
 * @author zhu.lei
 * @date 2021-05-05 14:00
 */
@Slf4j
public class KafkaKeyCount {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);
        env.setParallelism(2);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.1.19:9092");
        properties.setProperty("group.id", "flink_test1");
        properties.setProperty("auto.offset.reset", "earliest");
        KeyValueDeserializer serializationSchema = new KeyValueDeserializer();
        FlinkKafkaConsumer<Tuple3<String, String, Long>> consumer = new FlinkKafkaConsumer<Tuple3<String, String, Long>>("flink_siem", serializationSchema, properties);

        DataStream<Tuple3<String, String, Long>> stream = env.addSource(consumer, "kafka-source");

        MongoSink mongoSink = new MongoSink("localhost", 27017, "admin", "kafka-key-count");
        stream.assignTimestampsAndWatermarks(WatermarkStrategy.<Tuple3<String, String, Long>>forBoundedOutOfOrderness(Duration.ofMinutes(5))
                .withTimestampAssigner(new SerializableTimestampAssigner<Tuple3<String, String, Long>>() {
                    @Override
                    public long extractTimestamp(Tuple3<String, String, Long> element, long recordTimestamp) {
                        ObjectMapper mapper = new ObjectMapper();
                        AlertModel alert = null;
                        try {
                            alert = mapper.readValue(element.f1, AlertModel.class);
                        } catch (JsonProcessingException e) {
                            return System.currentTimeMillis();
                        }
                        if (alert == null || alert.getMetaModel() == null || alert.getMetaModel().getTimestamp() == null) {
                            return System.currentTimeMillis();
                        } else {
                            return alert.getMetaModel().getTimestamp();
                        }
                    }
                })).keyBy(new KeySelector<Tuple3<String, String, Long>, Object>() {
            @Override
            public Object getKey(Tuple3<String, String, Long> value) throws Exception {
                return value.f0;
            }
        }).window(TumblingProcessingTimeWindows.of(Time.minutes(1)))
                .reduce(new ReduceFunction<Tuple3<String, String, Long>>() {
                    @Override
                    public Tuple3<String, String, Long> reduce(Tuple3<String, String, Long> value1, Tuple3<String, String, Long> value2) throws Exception {
                        return new Tuple3<>(value1.f0, value1.f1, value1.f2 + value2.f2);
                    }
                })
                .addSink(mongoSink).name("mongo-sink");
        stream.print();
        env.execute("mongo-to-mongo");
    }
}
