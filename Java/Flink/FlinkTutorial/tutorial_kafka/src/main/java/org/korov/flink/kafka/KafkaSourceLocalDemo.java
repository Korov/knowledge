package org.korov.flink.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.korov.flink.common.deserialization.KeyValueLocalDeserializer;
import org.korov.flink.common.model.NameModel;
import org.korov.flink.common.sink.MongoSink;

import java.time.Duration;
import java.util.Properties;

/**
 * @author zhu.lei
 * @date 2021-05-05 14:00
 */
@Slf4j
public class KafkaSourceLocalDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);
        env.setParallelism(1);

        KafkaSource<Tuple3<String, String, Long>> kafkaSource = KafkaSource.<Tuple3<String, String, Long>>builder()
                .setBootstrapServers("korov-linux.org:9092")
                .setGroupId("korov-consumer")
                .setStartingOffsets(OffsetsInitializer.committedOffsets(OffsetResetStrategy.EARLIEST))
                .setTopics("korov-demo")
                .setDeserializer(new KeyValueLocalDeserializer())
                .build();

        DataStream<Tuple3<String, String, Long>> stream = env.fromSource(kafkaSource,WatermarkStrategy.<Tuple3<String, String, Long>>forBoundedOutOfOrderness(Duration.ofMinutes(5))
                .withTimestampAssigner(new SerializableTimestampAssigner<Tuple3<String, String, Long>>() {
                    @Override
                    public long extractTimestamp(Tuple3<String, String, Long> element, long recordTimestamp) {
                        log.info("key:{}, value:{}, count:{}", element.f0, element.f1, element.f2);
                        return Long.parseLong(element.f1);
                    }
                }),"kafka-source");

        MongoSink mongoSink = new MongoSink("localhost", 27017, "admin", "key-count");
        stream.keyBy(new KeySelector<Tuple3<String, String, Long>, Object>() {
            @Override
            public Object getKey(Tuple3<String, String, Long> value) throws Exception {
                return value.f0;
            }
        }).window(TumblingEventTimeWindows.of(Time.minutes(1)))
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
