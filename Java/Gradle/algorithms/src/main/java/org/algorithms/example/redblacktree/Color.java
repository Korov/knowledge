package org.algorithms.example.redblacktree;

/**
 * @author korov
 */
public enum Color {
    /**
     * 红色
     */
    RED(0),

    /**
     * 黑色
     */
    BLACK(1);

    private final Integer value;


    Color(int value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
