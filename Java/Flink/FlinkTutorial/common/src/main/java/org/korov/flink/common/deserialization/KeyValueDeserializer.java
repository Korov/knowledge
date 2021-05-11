package org.korov.flink.common.deserialization;

import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class KeyValueDeserializer implements KafkaDeserializationSchema<Tuple3<String, String, Long>> {
    @Override
    public boolean isEndOfStream(Tuple3<String, String, Long> nextElement) {
        return false;
    }

    @Override
    public Tuple3<String, String, Long> deserialize(ConsumerRecord<byte[], byte[]> record) throws Exception {
        String key = new String(record.key());
        String value = new String(record.value());
        return new Tuple3<>(key, value, 1L);
    }

    @Override
    public TypeInformation<Tuple3<String, String, Long>> getProducedType() {
        return TypeInformation.of(new TypeHint<Tuple3<String, String, Long>>() {
        });
    }
}
