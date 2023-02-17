package org.korov.flink.name.count.deserialization;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.metrics.Gauge;
import org.apache.flink.metrics.MetricGroup;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.korov.flink.name.count.model.NameModel;

import java.io.IOException;


/**
 * 将kafka中的数据解析成对应的数据结构
 */
@Slf4j
public class KeyValueDeserializer implements KafkaRecordDeserializationSchema<Tuple2<String, String>> {
    private transient long kafkaDeserializationCount = 0L;

    @Override
    public TypeInformation<Tuple2<String, String>> getProducedType() {
        return TypeInformation.of(new TypeHint<Tuple2<String, String>>() {
        });
    }

    @Override
    public void open(DeserializationSchema.InitializationContext context) throws Exception {
        KafkaRecordDeserializationSchema.super.open(context);
        MetricGroup metricGroup = context.getMetricGroup();
        metricGroup.gauge("kafkaDeserializationCount", (Gauge<Long>) () -> kafkaDeserializationCount);
    }

    /**
     * key是kafka的key，NameModel中对应的数据是告警名称，告警元数据，告警的时间，以后后面的数量
     *
     * @param record The ConsumerRecord to deserialize.
     * @param out The collector to put the resulting messages.
     */
    @Override
    public void deserialize(ConsumerRecord<byte[], byte[]> record, Collector<Tuple2<String, String>> out) {
        String value = new String(record.value());
        Tuple2<String,String> result = Tuple2.of(null, value);
        if (record.key() != null) {
            result.f0 = new String(record.key());
        }
        kafkaDeserializationCount++;
        out.collect(result);
    }
}
