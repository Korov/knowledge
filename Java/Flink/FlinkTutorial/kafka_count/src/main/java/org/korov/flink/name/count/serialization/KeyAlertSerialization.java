package org.korov.flink.name.count.serialization;

import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.metrics.Gauge;
import org.apache.flink.metrics.MetricGroup;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.korov.flink.name.count.model.NameModel;

import javax.swing.text.html.Option;
import java.nio.charset.StandardCharsets;

import java.util.Optional;

public class KeyAlertSerialization implements KafkaRecordSerializationSchema<Tuple3<String, NameModel, Long>> {
    private final String topic;

    private transient long kafkaSerializationCount = 0L;

    public KeyAlertSerialization(String topic) {
        this.topic = Optional.ofNullable(topic).orElse("demo_topic");
    }

    @Override
    public void open(SerializationSchema.InitializationContext context, KafkaSinkContext sinkContext) throws Exception {
        MetricGroup metricGroup = context.getMetricGroup();
        metricGroup.gauge("kafkaSerializationCount", (Gauge<Long>) () -> kafkaSerializationCount);
        KafkaRecordSerializationSchema.super.open(context, sinkContext);
    }

    @Override
    public ProducerRecord<byte[], byte[]> serialize(Tuple3<String, NameModel, Long> input, KafkaSinkContext kafkaSinkContext, Long aLong) {
        String key = Optional.ofNullable(input.f0).orElse("");
        String message = "";
        if (input.f1 != null) {
            message = Optional.ofNullable(input.f1.getMessage()).orElse("");
        }
        kafkaSerializationCount++;
        return new ProducerRecord<>(this.topic, key.getBytes(StandardCharsets.UTF_8), message.getBytes(StandardCharsets.UTF_8));
    }
}
