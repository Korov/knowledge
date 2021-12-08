package org.algorithms.example.redblacktree;

import lombok.extern.slf4j.Slf4j;
import org.algorithms.example.tree.TreeNode;

/**
 * @author korov
 */
@Slf4j
public class RedBlackUtil {
    public static final RedBlackNode NULL_NODE = new RedBlackNode();

    /**
     * 对node进行左旋
     */
    public static RedBlackNode leftRotate(RedBlackNode root, RedBlackNode node) {
        RedBlackNode rightNode = node.right;
        if (rightNode == null) {
            return root;
        }
        node.right = rightNode.left;
        if (rightNode.left != NULL_NODE) {
            rightNode.left.parent = node;
        }
        rightNode.left = node;
        rightNode.parent = node.parent;
        node.parent = rightNode;
        if (rightNode.parent == NULL_NODE) {
            return rightNode;
        } else {
            return root;
        }
    }

    /**
     * 对node进行右旋
     */
    public static RedBlackNode rightRotate(RedBlackNode root, RedBlackNode node) {
        RedBlackNode leftNode = node.left;
        if (leftNode == null) {
            return root;
        }
        node.left = leftNode.right;
        if (leftNode.right != NULL_NODE) {
            leftNode.right.parent = node;
        }
        leftNode.right = node;
        leftNode.parent = node.parent;
        node.parent = leftNode;
        if (leftNode.parent == NULL_NODE) {
            return leftNode;
        } else {
            return root;
        }
    }

    /**
     * 找到合适的位置将节点插入树中
     */
    public static RedBlackNode rbInsert(RedBlackNode root, RedBlackNode insertNode) {
        if (root == null) {
            root = insertNode;
            insertNode.color = Color.BLACK;
            return root;
        }
        RedBlackNode insertIndex = root;
        RedBlackNode parentInsertIndex = insertIndex.parent;
        // 找到插入点
        while (insertIndex != NULL_NODE) {
            parentInsertIndex = insertIndex;
            if (insertNode.value.compareTo(insertIndex.value) > 0) {
                insertIndex = insertIndex.right;
            } else {
                insertIndex = insertIndex.left;
            }
        }
        insertNode.parent = parentInsertIndex;
        if (insertNode.value.compareTo(parentInsertIndex.value) > 0) {
            parentInsertIndex.right = insertNode;
        } else {
            parentInsertIndex.left = insertNode;
        }
        insertNode.color = Color.RED;
        return rbInsertFixup(root, insertNode);
    }

    /**
     * 保持树的红黑性
     */
    private static RedBlackNode rbInsertFixup(RedBlackNode root, RedBlackNode insertNode) {
        while (insertNode.parent.color == Color.RED) {
            if (insertNode.parent == insertNode.parent.parent.left) {
                RedBlackNode uncleNode = insertNode.parent.parent.right;
                if (uncleNode.color == Color.RED) {
                    insertNode.parent.color = Color.BLACK;
                    uncleNode.color = Color.BLACK;
                    insertNode.parent.parent.color = Color.RED;
                    uncleNode = insertNode.parent.parent;
                } else if (insertNode == insertNode.parent.right) {
                    insertNode = insertNode.parent;
                    root = leftRotate(root, insertNode);
                }
                insertNode.parent.color = Color.BLACK;
                insertNode.parent.parent.color = Color.RED;
                root = rightRotate(root, insertNode);
            } else {
                RedBlackNode uncleNode = insertNode.parent.parent.left;
                if (uncleNode.color == Color.RED) {
                    insertNode.parent.color = Color.BLACK;
                    uncleNode.color = Color.BLACK;
                    insertNode.parent.parent.color = Color.RED;
                    uncleNode = insertNode.parent.parent;
                } else if (insertNode == insertNode.parent.left) {
                    insertNode = insertNode.parent;
                    root = rightRotate(root, insertNode);
                }
                insertNode.parent.color = Color.BLACK;
                insertNode.parent.parent.color = Color.RED;
                root = leftRotate(root, insertNode);
            }
        }
        return root;
    }

    /**
     * 中序遍历
     *
     * @param tree
     */
    public static void inorderTraversal(RedBlackNode tree) {
        if (tree == null) {
            return;
        }
        inorderTraversal(tree.left);
        log.info(String.valueOf(tree.value));
        inorderTraversal(tree.right);
    }
}
