package org.korov.flink.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zhu.lei
 * @date 2021-05-07 17:38
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlinkAlertModel {
    @JsonProperty("__meta__")
    private MetaModel metaModel;

    @JsonProperty("rule_name")
    private String ruleName;

    /**
     * @author zhu.lei
     * @date 2021-05-07 17:39
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MetaModel {
        @JsonProperty("timestamp")
        private Long timestamp;

        @JsonProperty("uuid")
        private String uuid;
    }
}
