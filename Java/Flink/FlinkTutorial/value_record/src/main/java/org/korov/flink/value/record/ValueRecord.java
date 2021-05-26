package org.korov.flink.value.record;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.korov.flink.common.deserialization.KeyAlertDeserializer;
import org.korov.flink.common.model.NameModel;
import org.korov.flink.common.sink.KeyAlertMongoSink;

import java.util.Properties;

/**
 * @author zhu.lei
 * @date 2021-05-26 11:06
 */
@Slf4j
public class ValueRecord {
    // private static final String MONGO_HOST = "localhost";
    private static final String MONGO_HOST = "mongo-flink";

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);
        env.setParallelism(2);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.1.19:9092");
        properties.setProperty("group.id", "value-record");
        properties.setProperty("auto.offset.reset", "earliest");
        KeyAlertDeserializer serializationSchema = new KeyAlertDeserializer();
        FlinkKafkaConsumer<Tuple3<String, NameModel, Long>> consumer = new FlinkKafkaConsumer<Tuple3<String, NameModel, Long>>("flink_siem", serializationSchema, properties);

        DataStream<Tuple3<String, NameModel, Long>> stream = env.addSource(consumer, "kafka-source");

        KeyAlertMongoSink mongoNameSink = new KeyAlertMongoSink(MONGO_HOST, 27017, "admin", "value-record");
        stream.addSink(mongoNameSink).name("mongo-name-sink");
        env.execute("kafka-name-count");
    }
}
