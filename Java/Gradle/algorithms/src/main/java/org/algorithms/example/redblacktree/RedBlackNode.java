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
        this(RedBlackUtil.NULL_NODE, RedBlackUtil.NULL_NODE, RedBlackUtil.NULL_NODE, null, null);
    }

    public RedBlackNode(Color color, Integer value) {
        this(RedBlackUtil.NULL_NODE, RedBlackUtil.NULL_NODE, RedBlackUtil.NULL_NODE, color, value);
    }

    public RedBlackNode(RedBlackNode parent, RedBlackNode left, RedBlackNode right, Color color, Integer value) {
        this.parent = parent;
        this.left = left;
        this.right = right;
        this.color = color;
        this.value = value;
    }
}
