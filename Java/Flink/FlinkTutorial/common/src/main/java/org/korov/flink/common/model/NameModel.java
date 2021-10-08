package org.korov.flink.common.model;

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
}
