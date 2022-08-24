package org.korov.flink.name.count.serialization;

import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class SimpleSerialization implements KafkaRecordSerializationSchema<Tuple2<String, String>> {
    private final String topic;

    public SimpleSerialization(String topic) {
        this.topic = Optional.ofNullable(topic).orElse("demo_topic");
    }

    @Override
    public void open(SerializationSchema.InitializationContext context, KafkaSinkContext sinkContext) throws Exception {
        KafkaRecordSerializationSchema.super.open(context, sinkContext);
    }

    @Override
    public ProducerRecord<byte[], byte[]> serialize(Tuple2<String, String> input, KafkaSinkContext kafkaSinkContext, Long aLong) {
        String key = Optional.ofNullable(input.f0).orElse("");
        String message = Optional.ofNullable(input.f1).orElse("");
        return new ProducerRecord<>(this.topic, key.getBytes(StandardCharsets.UTF_8), message.getBytes(StandardCharsets.UTF_8));
    }
}
