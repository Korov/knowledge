package org.algorithms.example.redblacktree;

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
        return "RedBlockNode{" +
                "parent=" + (parent == null ? "empty parent" : parent) +
                ", left=" + (left == null ? "empty left" : left) +
                ", right=" + (right == null ? "empty right" : right) +
                ", color=" + (color == null ? "empty color" : color) +
                ", value=" + (value == null ? "empty value" : value) +
                '}';
    }
}
