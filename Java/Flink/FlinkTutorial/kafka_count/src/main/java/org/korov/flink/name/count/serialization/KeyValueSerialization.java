package org.korov.flink.name.count.serialization;

import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.metrics.Gauge;
import org.apache.flink.metrics.MetricGroup;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.korov.flink.name.count.model.NameModel;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class KeyValueSerialization implements KafkaRecordSerializationSchema<Tuple2<String, String>> {
    private final String topic;

    private transient long kafkaSerializationCount = 0L;

    public KeyValueSerialization(String topic) {
        this.topic = Optional.ofNullable(topic).orElse("demo_topic");
    }

    @Override
    public void open(SerializationSchema.InitializationContext context, KafkaSinkContext sinkContext) throws Exception {
        MetricGroup metricGroup = context.getMetricGroup();
        metricGroup.gauge("kafkaSerializationCount", (Gauge<Long>) () -> kafkaSerializationCount);
        KafkaRecordSerializationSchema.super.open(context, sinkContext);
    }

    @Override
    public ProducerRecord<byte[], byte[]> serialize(Tuple2<String, String> input, KafkaSinkContext kafkaSinkContext, Long aLong) {
        String message = Optional.of(input.f1).orElse("");
        kafkaSerializationCount++;
        if (input.f0 == null) {
            return new ProducerRecord<>(this.topic, null, message.getBytes(StandardCharsets.UTF_8));
        }else {
            return new ProducerRecord<>(this.topic, input.f0.getBytes(StandardCharsets.UTF_8), message.getBytes(StandardCharsets.UTF_8));
        }
    }
}
