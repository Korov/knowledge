package org.algorithms.example.intervaltree;

import lombok.Getter;

/**
 * @author zhu.lei
 * @date 2023-07-28 15:13
 */
@Getter
public class IntervalValue<T extends Comparable<T>, V> implements Comparable<IntervalValue<T, V>> {
    private T low;
    private T high;
    private T max;
    private V value;

    public IntervalValue(T low, T high, V value) {
        this.low = low;
        this.high = high;
        this.value = value;
    }

    public void setMax(T max) {
        this.max = max;
    }

    @Override
    public int compareTo(IntervalValue<T, V> other) {
        return this.low.compareTo(other.low);
    }
}
