package org.algorithms.example.tree;

/**
 * @author zhu.lei
 * @date 2021-11-26 17:33
 */
public class TreeNode<K extends Comparable<K>> {
    public K value;
    private TreeNode<K> left;
    private TreeNode<K> right;
    private TreeNode<K> parent;

    public TreeNode() {
    }

    public TreeNode(K value) {
        this.value = value;
    }

    public TreeNode<K> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<K> left) {
        this.left = left;
        left.setParent(this);
    }

    public TreeNode<K> getRight() {
        return right;
    }

    public void setRight(TreeNode<K> right) {
        this.right = right;
        right.setParent(this);
    }

    public TreeNode<K> getParent() {
        return parent;
    }

    public void setParent(TreeNode<K> parent) {
        this.parent = parent;
    }

    public K getValue() {
        return value;
    }

    public TreeNode<K> treeSearch(TreeNode<K> node, K key) {
        if (node == null || node.getValue() == null || key == null) {
            return null;
        }
        if (node.getValue().equals(key)) {
            return node;
        }
        if (node.getValue().compareTo(key) > 0) {
            return treeSearch(node.left, key);
        } else {
            return treeSearch(node.right, key);
        }
    }
}
