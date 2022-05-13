package org.korov.flink.name.count.enums;

/**
 * @author zhu.lei
 * @date 2021-06-01 15:43
 */
public enum SinkType {
    KEY(0),
    KEY_NAME(1),
    KEY_NAME_VALUE(2);

    private final int code;

    SinkType(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
