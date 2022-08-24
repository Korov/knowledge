package org.korov.flink.name.count.deserialization;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;


/**
 * 将kafka中的数据解析成对应的数据结构
 */
@Slf4j
public class SimpleDeserializer implements KafkaRecordDeserializationSchema<Tuple2<String, String>> {
    @Override
    public TypeInformation<Tuple2<String, String>> getProducedType() {
        return TypeInformation.of(new TypeHint<Tuple2<String, String>>() {
        });
    }

    @Override
    public void open(DeserializationSchema.InitializationContext context) throws Exception {
        KafkaRecordDeserializationSchema.super.open(context);
    }

    /**
     * key是kafka的key，NameModel中对应的数据是告警名称，告警元数据，告警的时间，以后后面的数量
     *
     * @param record The ConsumerRecord to deserialize.
     * @param out    The collector to put the resulting messages.
     */
    @Override
    public void deserialize(ConsumerRecord<byte[], byte[]> record, Collector<Tuple2<String, String>> out) {
        String key = "";
        String message = "";
        if (record != null) {
            if (record.key() != null) {
                key = new String(record.key());
            }
            if (record.value() != null) {
                message = new String(record.value());
            }
        }
        out.collect(new Tuple2<>(key, message));
    }
}
