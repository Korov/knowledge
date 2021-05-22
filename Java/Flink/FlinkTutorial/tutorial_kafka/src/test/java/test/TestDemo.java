package test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.korov.flink.common.model.FlinkAlertModel;

/**
 * @author zhu.lei
 * @date 2021-05-07 19:50
 */
@Slf4j
public class TestDemo {
    @Test
    public void test() {
        String value = "{\"__meta__\":{\"rule_id\":\"a04a60774ca14628b260c867719c0235\",\"rule\":\"( (appname:windows) AND (json.event_id:3) AND ( (json.event_data.DestinationPort:22) OR (json.event_data.DestinationPort:23) OR (json.event_data.DestinationPort:25) OR (json.event_data.DestinationPort:135) OR (json.event_data.DestinationPort:3389) OR (json.event_data.DestinationPort:5800) OR (json.event_data.DestinationPort:5900) OR (json.event_data.DestinationPort:8080) ) AND (json.event_data.Initiated:true) ) | fields ip, json.event_data.User, json.src_ip | rename ip as dst_ip, json.event_data.User as user, json.src_ip as src_ip | eval ti_mark = 1 | eval ip_addr = dst_ip | eval threat_classif = \\\"主机安全\\\" | eval extend_threat_classif = \\\"信息探测\\\" | eval threat_stage = 0 | eval threat_state = 0 | eval threat_level = 0 | eval __inner_alert__ = 0 | eval __inner_event__ = 1 | eval desc = \\\"T1043_Commonly_Used_Port：常用端口，通过常用的端口进行通信，可以绕过防火墙或网络检测系统，并与正常的网络活动相结合，以避免触发告警拦截。\\\" + dst_ip + \\\"\\\"| eval rule_name = \\\"T1043_Commonly_Used_Port\\\"\",\"window_size_ms\":0,\"uuid\":\"2e8d3d37-e8cb-437b-9539-983558aca10a\",\"timestamp\":1619486927023},\"threat_level\":0,\"rule_name\":\"T1043_Commonly_Used_Port\",\"extend_threat_classif\":\"信息探测\",\"threat_classif\":\"主机安全\",\"threat_state\":0,\"dst_ip\":\"192.168.1.23\",\"__inner_alert__\":0,\"__inner_event__\":1,\"ti_mark\":1,\"ip_addr\":\"192.168.1.23\",\"user\":\"NT AUTHORITY\\\\\\\\SYSTEM\",\"threat_stage\":0,\"desc\":\"T1043_Commonly_Used_Port：常用端口，通过常用的端口进行通信，可以绕过防火墙或网络检测系统，并与正常的网络活动相结合，以避免触发告警拦截。192.168.1.23\"}";
        ObjectMapper mapper = new ObjectMapper();
        FlinkAlertModel alert = null;
        try {
            alert = mapper.readValue(value, FlinkAlertModel.class);
        } catch (JsonProcessingException e) {
            log.error("error", e);
        }
        log.info(alert.getMetaModel().getTimestamp().toString());
    }
}
