package org.algorithms.example.redblacktree;

/**
 * @author korov
 */
public class RedBlackNode<K extends Comparable<K>> {
    public RedBlackNode<K> parent;
    public RedBlackNode<K> left;
    public RedBlackNode<K> right;
    public Color color;
    public K value;

    public RedBlackNode() {
        this(RedBlackUtil.NULL_NODE, RedBlackUtil.NULL_NODE, RedBlackUtil.NULL_NODE, null, null);
    }

    public RedBlackNode(Color color, K value) {
        this(RedBlackUtil.NULL_NODE, RedBlackUtil.NULL_NODE, RedBlackUtil.NULL_NODE, color, value);
    }

    public RedBlackNode(RedBlackNode<K> parent, RedBlackNode<K> left, RedBlackNode<K> right, Color color, K value) {
        this.parent = parent;
        this.left = left;
        this.right = right;
        this.color = color;
        this.value = value;
    }
}
