package org.korov.flink.name.count.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zhu.lei
 * @date 2021-05-22 12:43
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MapModel {
    @JsonProperty("id")
    private String id;

    @JsonProperty("endTime")
    private Long createTime;
}
