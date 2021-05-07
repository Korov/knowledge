package org.korov.flink.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zhu.lei
 * @date 2021-05-07 17:39
 */
@Data
public class MetaModel {
    @JsonProperty("timestamp")
    private Long timestamp;
}
