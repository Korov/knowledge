package org.algorithms.example.redblacktree;

import lombok.extern.slf4j.Slf4j;
import org.algorithms.example.tree.TreeNode;

import javax.print.attribute.standard.NumberUp;

/**
 * @author korov
 */
@Slf4j
public class RedBlackUtil {
    public static final RedBlackNode NULL_NODE = new RedBlackNode(null, null, null, Color.BLACK, null);

    /**
     * 对node进行左旋
     */
    public static RedBlackNode leftRotate(RedBlackNode root, RedBlackNode node) {
        RedBlackNode rightNode = node.right;
        node.right = rightNode.left;
        if (rightNode.left != NULL_NODE) {
            rightNode.left.parent = node;
        }

        rightNode.parent = node.parent;
        if (node.parent == NULL_NODE) {
            root = rightNode;
        } else if (node == node.parent.left) {
            node.parent.left = rightNode;
        } else {
            node.parent.right = rightNode;
        }
        rightNode.left = node;
        node.parent = rightNode;
        return root;
    }

    /**
     * 对node进行右旋
     */
    public static RedBlackNode rightRotate(RedBlackNode root, RedBlackNode node) {
        RedBlackNode leftNode = node.left;
        node.left = leftNode.right;
        if (leftNode.right != NULL_NODE) {
            leftNode.right.parent = node;
        }

        leftNode.parent = node.parent;
        if (node.parent == NULL_NODE) {
            root = leftNode;
        } else if (node == node.parent.left) {
            node.parent.left = leftNode;
        } else {
            node.parent.right = leftNode;
        }
        leftNode.right = node;
        node.parent = leftNode;
        return root;
    }

    /**
     * 找到合适的位置将节点插入树中
     */
    public static RedBlackNode rbInsert(RedBlackNode root, RedBlackNode insertNode) {

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
        if (parentInsertIndex == NULL_NODE) {
            root = insertIndex;
        } else if (insertNode.value.compareTo(parentInsertIndex.value) > 0) {
            parentInsertIndex.right = insertNode;
        } else {
            parentInsertIndex.left = insertNode;
        }
        insertNode.color = Color.RED;
        insertNode.left = NULL_NODE;
        insertNode.right = NULL_NODE;
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
                    insertNode = insertNode.parent.parent;
                } else if (insertNode == insertNode.parent.right) {
                    insertNode = insertNode.parent;
                    root = leftRotate(root, insertNode);
                } else {
                    insertNode.parent.color = Color.BLACK;
                    insertNode.parent.parent.color = Color.RED;
                    root = rightRotate(root, insertNode.parent.parent);
                }
            } else {
                RedBlackNode uncleNode = insertNode.parent.parent.left;
                if (uncleNode.color == Color.RED) {
                    insertNode.parent.color = Color.BLACK;
                    uncleNode.color = Color.BLACK;
                    insertNode.parent.parent.color = Color.RED;
                    insertNode = insertNode.parent.parent;
                } else if (insertNode == insertNode.parent.left) {
                    insertNode = insertNode.parent;
                    root = rightRotate(root, insertNode);
                } else {
                    insertNode.parent.color = Color.BLACK;
                    insertNode.parent.parent.color = Color.RED;
                    root = leftRotate(root, insertNode.parent.parent);
                }
            }
        }
        root.color = Color.BLACK;
        return root;
    }

    /**
     * 中序遍历
     *
     * @param tree
     */
    public static void inorderTraversal(RedBlackNode tree) {
        if (tree == NULL_NODE) {
            return;
        }
        inorderTraversal(tree.left);
        log.info(String.valueOf(tree.value));
        inorderTraversal(tree.right);
    }
}
