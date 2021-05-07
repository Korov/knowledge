package org.korov.flink.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zhu.lei
 * @date 2021-05-07 17:38
 */
@Data
public class AlertModel {
    @JsonProperty("__meta__")
    private MetaModel metaModel;

}
