package org.korov.flink.name.count.deserialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.metrics.Gauge;
import org.apache.flink.metrics.MetricGroup;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.korov.flink.common.utils.GZIPUtils;
import org.korov.flink.name.count.model.FlinkAlertModel;
import org.korov.flink.name.count.model.MapModel;
import org.korov.flink.name.count.model.NameModel;
import org.korov.flink.name.count.model.SPLAlertModel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * 将kafka中的数据解析成对应的数据结构
 */
@Slf4j
public class KeyAlertDeserializer implements KafkaRecordDeserializationSchema<Tuple3<String, NameModel, Long>> {
    private transient long kafkaDeserializationCount = 0L;

    @Override
    public TypeInformation<Tuple3<String, NameModel, Long>> getProducedType() {
        return TypeInformation.of(new TypeHint<Tuple3<String, NameModel, Long>>() {
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
    public void deserialize(ConsumerRecord<byte[], byte[]> record, Collector<Tuple3<String, NameModel, Long>> out) throws IOException {
        String key = new String(record.key());
        String value = new String(record.value());

        NameModel nameModel = new NameModel();
        nameModel.setMessage(value);
        ObjectMapper mapper = new ObjectMapper();
        switch (key) {
            case "flink_alert":
                FlinkAlertModel alert = null;
                try {
                    alert = mapper.readValue(value, FlinkAlertModel.class);
                } catch (JsonProcessingException e) {
                    log.error("parse key:[{}] value:[{}] failed", key, value, e);
                }
                if (alert == null || alert.getMetaModel() == null || alert.getMetaModel().getTimestamp() == null) {
                    log.error("parse key:[{}] value:[{}] failed", key, value);
                    nameModel.setTimestamp(System.currentTimeMillis());
                    nameModel.setName("null");
                } else {
                    nameModel.setTimestamp(alert.getMetaModel().getTimestamp());
                    nameModel.setName(alert.getRuleName());
                    nameModel.setUuid(alert.getMetaModel().getUuid());
                    nameModel.setRuleId(alert.getMetaModel().getRuleId());
                }
                break;
            case "spl_alert":
                SPLAlertModel alertModel = null;
                try {
                    alertModel = mapper.readValue(value, SPLAlertModel.class);
                } catch (JsonProcessingException e) {
                    log.error("parse key:[{}] value:[{}] failed", key, value, e);
                }
                if (alertModel == null || alertModel.getAlertName() == null || alertModel.getEndTime() == null) {
                    log.error("parse key:[{}] value:[{}] failed", key, value);
                    nameModel.setTimestamp(System.currentTimeMillis());
                    nameModel.setName("null");
                } else {
                    nameModel.setName(alertModel.getAlertName());
                    nameModel.setTimestamp(alertModel.getEndTime());
                    nameModel.setUuid(alertModel.getUuid());
                }
                break;
            case "nmap_data":
                MapModel model = null;
                byte[] bytes = GZIPUtils.uncompress(record.value());
                value = new String(bytes, StandardCharsets.UTF_8);
                nameModel.setMessage(value);
                try {
                    model = mapper.readValue(value, MapModel.class);
                } catch (JsonProcessingException e) {
                    log.error("parse key:[{}] value:[{}] failed", key, value, e);
                }
                if (model == null || model.getId() == null || model.getCreateTime() == null) {
                    log.error("parse key:[{}] value:[{}] failed", key, value);
                    nameModel.setTimestamp(System.currentTimeMillis());
                    nameModel.setName("null");
                } else {
                    nameModel.setName(model.getId());
                    nameModel.setTimestamp(model.getCreateTime());
                }
                break;
            default:
                log.error("parse key:[{}] value:[{}] failed", key, value);
                nameModel.setTimestamp(System.currentTimeMillis());
                nameModel.setName("null");
                break;
        }
        kafkaDeserializationCount++;
        out.collect(new Tuple3<>(key, nameModel, 1L));
    }
}
