package org.korov.flink.common.model;

import lombok.Data;

@Data
public class KeyValue {
    private String key;
    private Long timestamp;
    private Long count;
}