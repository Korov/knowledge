package org.algorithms.example.tree;

/**
 * @author zhu.lei
 * @date 2021-11-26 17:33
 */
public class TreeNode<K> {
    private TreeNode<K> left;
    private TreeNode<K> right;
    private TreeNode<K> parent;
    public K value;

    public TreeNode() {
    }

    public TreeNode(K value) {
        this.value = value;
    }

    public void setLeft(TreeNode<K> left) {
        this.left = left;
        left.setParent(this);
    }

    public void setRight(TreeNode<K> right) {
        this.right = right;
        right.setParent(this);
    }

    public void setParent(TreeNode<K> parent) {
        this.parent = parent;
    }

    public TreeNode<K> getLeft() {
        return left;
    }

    public TreeNode<K> getRight() {
        return right;
    }

    public TreeNode<K> getParent() {
        return parent;
    }
}
