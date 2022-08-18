package org.korov.flink.common.deserialization;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;

public class KeyValueDeserializer implements KafkaRecordDeserializationSchema<Tuple3<String, String, Long>> {
    @Override
    public TypeInformation<Tuple3<String, String, Long>> getProducedType() {
        return TypeInformation.of(new TypeHint<Tuple3<String, String, Long>>() {
        });
    }

    @Override
    public void open(DeserializationSchema.InitializationContext context) throws Exception {
        KafkaRecordDeserializationSchema.super.open(context);
    }

    @Override
    public void deserialize(ConsumerRecord<byte[], byte[]> consumerRecord, Collector<Tuple3<String, String, Long>> collector) throws IOException {
        String key = new String(consumerRecord.key());
        String value = new String(consumerRecord.value());
        collector.collect(new Tuple3<>(key, value, 1L));
    }
}
