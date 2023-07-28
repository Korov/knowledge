package org.algorithms.example.intervaltree;

import org.algorithms.example.redblacktree.Color;

/**
 * @author zhu.lei
 * @date 2023-07-28 15:09
 */
public class IntervalTreeNode<T extends Comparable<T>, V> {
    public IntervalTreeNode<T, V> parent;
    public IntervalTreeNode<T, V> left;
    public IntervalTreeNode<T, V> right;
    public Color color;
    public IntervalValue<T, V> value;

    public T calcMax() {
        if (this.value.getMax() != null) {
            return value.getMax();
        }
        T currentMax = this.value.getHigh();
        if (this.left == IntervalTreeUtil.NULL_NODE && this.right == IntervalTreeUtil.NULL_NODE) {
            this.value.setMax(currentMax);
        } else if (this.left == IntervalTreeUtil.NULL_NODE) {
            T max = currentMax.compareTo(this.right.calcMax()) > 0 ? currentMax : this.right.calcMax();
            this.value.setMax(max);
        } else if (this.right == IntervalTreeUtil.NULL_NODE) {
            T max = currentMax.compareTo(this.left.calcMax()) > 0 ? currentMax : this.left.calcMax();
            this.value.setMax(max);
        } else {
            T max = currentMax.compareTo(this.left.calcMax()) > 0 ? currentMax : this.left.calcMax();
            max = max.compareTo(this.right.calcMax()) > 0 ? max : this.right.calcMax();
            this.value.setMax(max);
        }
        return this.value.getMax();
    }


    public IntervalTreeNode() {
        this(IntervalTreeUtil.NULL_NODE, IntervalTreeUtil.NULL_NODE, IntervalTreeUtil.NULL_NODE, null, null);
    }

    public IntervalTreeNode(Color color, IntervalValue<T, V> value) {
        this(IntervalTreeUtil.NULL_NODE, IntervalTreeUtil.NULL_NODE, IntervalTreeUtil.NULL_NODE, color, value);
    }

    public IntervalTreeNode(IntervalTreeNode<T, V> parent, IntervalTreeNode<T, V> left, IntervalTreeNode<T, V> right, Color color, IntervalValue<T, V> value) {
        this.parent = parent;
        this.left = left;
        this.right = right;
        this.color = color;
        this.value = value;
    }
}
