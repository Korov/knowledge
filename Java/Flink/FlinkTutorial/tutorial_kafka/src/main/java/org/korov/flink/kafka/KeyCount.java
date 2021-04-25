package org.korov.flink.kafka;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.util.serialization.TypeInformationKeyValueSerializationSchema;
import org.korov.flink.deserialization.KeyValueDeserializer;

import java.util.Properties;

public class KeyCount {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "korov-linux.org:9092");
        properties.setProperty("group.id", "flink_test");
        KeyValueDeserializer serializationSchema = new KeyValueDeserializer();
        FlinkKafkaConsumer<Tuple3<String, String, Long>> consumer = new FlinkKafkaConsumer<Tuple3<String, String, Long>>("test", serializationSchema, properties);
        consumer.setStartFromLatest();

        DataStream<Tuple3<String, String, Long>> stream = env.addSource(consumer);
        stream.print();
        env.execute();
    }
}
