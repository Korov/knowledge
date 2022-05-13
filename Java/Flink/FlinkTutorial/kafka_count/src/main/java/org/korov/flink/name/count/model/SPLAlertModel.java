package org.korov.flink.name.count.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zhu.lei
 * @date 2021-05-22 12:37
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SPLAlertModel {
    @JsonProperty("alert_name")
    private String alertName;

    @JsonProperty("end_time")
    private Long endTime;

    @JsonProperty("uuid")
    private String uuid;
}
