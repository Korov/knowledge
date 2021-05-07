package org.korov.flink.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

/**
 * @author zhu.lei
 * @date 2021-05-07 17:38
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlertModel {
    @JsonProperty("__meta__")
    private MetaModel metaModel;
}
