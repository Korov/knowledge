package org.korov.flink.name.count.serialization;

import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.korov.flink.name.count.model.NameModel;

import javax.swing.text.html.Option;
import java.nio.charset.StandardCharsets;

import java.util.Optional;

public class KeyAlertSerialization implements KafkaRecordSerializationSchema<Tuple3<String, NameModel, Long>> {
    private final String topic;

    public KeyAlertSerialization(String topic) {
        this.topic = Optional.ofNullable(topic).orElse("demo_topic");
    }

    @Override
    public void open(SerializationSchema.InitializationContext context, KafkaSinkContext sinkContext) throws Exception {
        KafkaRecordSerializationSchema.super.open(context, sinkContext);
    }

    @Override
    public ProducerRecord<byte[], byte[]> serialize(Tuple3<String, NameModel, Long> input, KafkaSinkContext kafkaSinkContext, Long aLong) {
        String key = Optional.ofNullable(input.f0).orElse("");
        String message = "";
        if (input.f1 != null) {
            message = Optional.ofNullable(input.f1.getMessage()).orElse("");
        }
        return new ProducerRecord<>(this.topic, key.getBytes(StandardCharsets.UTF_8), message.getBytes(StandardCharsets.UTF_8));
    }
}
