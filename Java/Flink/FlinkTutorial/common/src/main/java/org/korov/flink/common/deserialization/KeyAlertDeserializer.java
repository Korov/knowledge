package org.korov.flink.common.deserialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.korov.flink.common.model.FlinkAlertModel;
import org.korov.flink.common.model.MapModel;
import org.korov.flink.common.model.NameModel;
import org.korov.flink.common.model.SPLAlertModel;

@Slf4j
public class KeyAlertDeserializer implements KafkaDeserializationSchema<Tuple3<String, NameModel, Long>> {
    @Override
    public boolean isEndOfStream(Tuple3<String, NameModel, Long> nextElement) {
        return false;
    }

    @Override
    public Tuple3<String, NameModel, Long> deserialize(ConsumerRecord<byte[], byte[]> record) throws Exception {
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
                }
                break;
            case "spl_alert":
                SPLAlertModel alertModel = null;
                try {
                    alertModel = mapper.readValue(value, SPLAlertModel.class);
                } catch (JsonProcessingException e) {
                    log.error("parse key:[{}] value:[{}] failed", key, value, e);
                }
                if (alertModel == null || alertModel.getAlertName() == null || alertModel.getCreateTime() == null) {
                    log.error("parse key:[{}] value:[{}] failed", key, value);
                    nameModel.setTimestamp(System.currentTimeMillis());
                    nameModel.setName("null");
                } else {
                    nameModel.setName(alertModel.getAlertName());
                    nameModel.setTimestamp(alertModel.getCreateTime());
                }
                break;
            case "nmap_data":
                MapModel model = null;
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
        return new Tuple3<>(key, nameModel, 1L);
    }

    @Override
    public TypeInformation<Tuple3<String, NameModel, Long>> getProducedType() {
        return TypeInformation.of(new TypeHint<Tuple3<String, NameModel, Long>>() {
        });
    }
}
