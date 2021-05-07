package org.korov.flink.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zhu.lei
 * @date 2021-05-07 17:39
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaModel {
    @JsonProperty("timestamp")
    private Long timestamp;
}
