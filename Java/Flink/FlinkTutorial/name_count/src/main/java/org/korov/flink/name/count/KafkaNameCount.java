package org.korov.flink.name.count;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.korov.flink.common.deserialization.KeyAlertDeserializer;
import org.korov.flink.common.deserialization.KeyValueDeserializer;
import org.korov.flink.common.model.FlinkAlertModel;
import org.korov.flink.common.model.NameModel;
import org.korov.flink.common.sink.KeyAlertMongoSink;
import org.korov.flink.common.sink.MongoSink;

import java.time.Duration;
import java.util.Properties;

/**
 * @author zhu.lei
 * @date 2021-05-05 14:00
 */
@Slf4j
public class KafkaNameCount {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);
        env.setParallelism(2);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.1.19:9092");
        properties.setProperty("group.id", "kafka-name-count");
        properties.setProperty("auto.offset.reset", "earliest");
        KeyAlertDeserializer serializationSchema = new KeyAlertDeserializer();
        FlinkKafkaConsumer<Tuple3<String, NameModel, Long>> consumer = new FlinkKafkaConsumer<Tuple3<String, NameModel, Long>>("flink_siem", serializationSchema, properties);

        DataStream<Tuple3<String, NameModel, Long>> stream = env.addSource(consumer, "kafka-source");

        KeyAlertMongoSink mongoSink = new KeyAlertMongoSink("mongo-flink", 27017, "admin", "kafka-name-count");
        stream.assignTimestampsAndWatermarks(WatermarkStrategy.<Tuple3<String, NameModel, Long>>forBoundedOutOfOrderness(Duration.ofMinutes(5))
                .withTimestampAssigner(new SerializableTimestampAssigner<Tuple3<String, NameModel, Long>>() {
                    @Override
                    public long extractTimestamp(Tuple3<String, NameModel, Long> element, long recordTimestamp) {
                        return element.f1.getTimestamp();
                    }
                })).keyBy(new KeySelector<Tuple3<String, NameModel, Long>, Object>() {
            @Override
            public Object getKey(Tuple3<String, NameModel, Long> value) throws Exception {
                return Joiner.on("-").join(value.f0, value.f1.getName());
            }
        }).window(TumblingProcessingTimeWindows.of(Time.minutes(1)))
                .reduce(new ReduceFunction<Tuple3<String, NameModel, Long>>() {
                    @Override
                    public Tuple3<String, NameModel, Long> reduce(Tuple3<String, NameModel, Long> value1, Tuple3<String, NameModel, Long> value2) throws Exception {
                        return new Tuple3<>(value1.f0, value1.f1, value1.f2 + value2.f2);
                    }
                })
                .addSink(mongoSink).name("mongo-sink");
        stream.print();
        env.execute("kafka-name-count");
    }
}
