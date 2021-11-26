package org.algorithms.example.redblacktree;

import com.google.common.base.MoreObjects;

/**
 * @author korov
 */
public class RedBlackNode {
    public RedBlackNode parent;
    public RedBlackNode left;
    public RedBlackNode right;
    public Color color;
    public Integer value;

    public RedBlackNode() {
    }

    public RedBlackNode(Color color, Integer value) {
        this(null, null, null, color, value);
    }

    public RedBlackNode(RedBlackNode parent, RedBlackNode left, RedBlackNode right, Color color, Integer value) {
        this.parent = parent;
        this.left = left;
        this.right = right;
        this.color = color;
        this.value = value;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("parent", parent)
                .add("left", left)
                .add("right", right)
                .add("color", color)
                .add("value", value)
                .toString();
    }
}
