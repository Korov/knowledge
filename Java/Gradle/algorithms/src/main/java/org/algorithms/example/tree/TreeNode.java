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
        if (left != null) {
            left.setParent(this);
        }
    }

    public TreeNode<K> getRight() {
        return right;
    }

    public void setRight(TreeNode<K> right) {
        this.right = right;
        if (right != null) {
            right.setParent(this);
        }
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
        while (node != null && key != null && key.compareTo(node.getValue()) != 0) {
            if (key.compareTo(node.getValue()) < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node;
    }

    public TreeNode<K> min(TreeNode<K> root) {
        TreeNode<K> result = root;
        while (result.left != null) {
            result = result.left;
        }
        return result;
    }

    /**
     * 按照中序遍历查找他的后继
     *
     * @param node
     * @return
     */
    public TreeNode<K> successor(TreeNode<K> node) {
        if (node.right != null) {
            return min(node.right);
        }
        TreeNode<K> parent = node.getParent();
        while (parent != null && node == parent.right) {
            node = parent;
            parent = parent.getParent();
        }
        return parent;
    }

    /**
     * 按照中序遍历查找他的前驱
     *
     * @param node
     * @return
     */
    public TreeNode<K> predecessor(TreeNode<K> node) {
        if (node.left != null) {
            return max(node.left);
        }
        TreeNode<K> parent = node.getParent();
        while (parent != null && node == parent.left) {
            node = parent;
            parent = parent.getParent();
        }
        return parent;
    }

    public TreeNode<K> max(TreeNode<K> root) {
        TreeNode<K> result = root;
        while (result.right != null) {
            result = result.right;
        }
        return result;
    }

    public TreeNode<K> insert(TreeNode<K> root, TreeNode<K> node) {
        if (root == null) {
            root = node;
            return root;
        }
        TreeNode<K> insertIndex = root;
        TreeNode<K> parentInsertIndex = null;
        while (insertIndex != null) {
            parentInsertIndex = insertIndex;
            if (insertIndex.getValue().compareTo(node.getValue()) < 0) {
                insertIndex = insertIndex.right;
            } else {
                insertIndex = insertIndex.left;
            }
        }
        if (parentInsertIndex.getValue().compareTo(node.getValue()) < 0) {
            parentInsertIndex.setRight(node);
        } else {
            parentInsertIndex.setLeft(node);
        }
        return root;
    }

    /**
     * 在二叉树root中，用placeNode子树替换placedNode子树
     *
     * @param root       树
     * @param placedNode 被替换的子树
     * @param placeNode  替换的子树
     */
    public TreeNode<K> transplant(TreeNode<K> root, TreeNode<K> placedNode, TreeNode<K> placeNode) {
        // 先把 placeNode 和父母分开
        if (placeNode != null && placeNode.getParent() != null) {
            if (placeNode == placeNode.getParent().getLeft()) {
                placeNode.getParent().setLeft(null);
            } else {
                placeNode.getParent().setRight(null);
            }
            placeNode.setParent(null);
        }
        if (placedNode.getParent() == null) {
            // placedNode 是根节点，直接替换掉
            root = placeNode;
        } else if (placedNode == placedNode.getParent().getLeft()) {
            placedNode.getParent().setLeft(placeNode);
        } else {
            placedNode.getParent().setRight(placeNode);
        }
        placedNode.setParent(null);
        return root;
    }

    public TreeNode<K> delete(TreeNode<K> root, TreeNode<K> deleteNode) {
        if (deleteNode.getLeft() == null) {
            root = transplant(root, deleteNode, deleteNode.getRight());
        } else if (deleteNode.getRight() == null) {
            root = transplant(root, deleteNode, deleteNode.getLeft());
        } else {
            TreeNode<K> successorNode = min(deleteNode.getRight());
            if (successorNode.getParent() != deleteNode) {
                // 此处实际是把后继节点删除，但是保留后继节点的引用
                root = transplant(root, successorNode, successorNode.getRight());
                successorNode.setRight(deleteNode.getRight());
            }
            // 用后继节点的子树替换删除节点的子树达到删除的目的
            root = transplant(root, deleteNode, successorNode);
            successorNode.setLeft(deleteNode.getLeft());
        }
        return root;
    }
}
