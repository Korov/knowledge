package org.korov.flink.common.model;

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

    @JsonProperty("create_time")
    private Long createTime;
}
