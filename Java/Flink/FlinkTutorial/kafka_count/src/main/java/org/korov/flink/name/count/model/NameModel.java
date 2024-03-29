package org.korov.flink.name.count.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhu.lei
 * @date 2021-05-22 12:33
 */
@Getter
@Setter
public class NameModel {
    private String name;
    private Long timestamp;
    private String message;
    private String uuid;
    private String ruleId;
    private JsonNode ruleInfo;

    private Long minTime = Long.MAX_VALUE;

    private Long maxTime = Long.MIN_VALUE;
}
