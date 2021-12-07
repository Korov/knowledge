package org.algorithms.example.redblacktree;

import lombok.extern.slf4j.Slf4j;
import org.algorithms.example.tree.TreeNode;

/**
 * @author korov
 */
@Slf4j
public class RedBlackUtil {
    /**
     * 对node进行左旋
     */
    public static RedBlackNode leftRotate(RedBlackNode tree, RedBlackNode node) {
        RedBlackNode leftNodeTemp = node.left;
        if (leftNodeTemp == null) {
            return tree;
        }
        node.right = leftNodeTemp.left;
        leftNodeTemp.left = node;
        leftNodeTemp.parent = node.parent;
        node.parent = leftNodeTemp;
        if (leftNodeTemp.parent == null) {
            return leftNodeTemp;
        } else {
            return tree;
        }
    }

    /**
     * 对node进行右旋
     */
    public static RedBlackNode rightRotate(RedBlackNode tree, RedBlackNode node) {
        RedBlackNode leftNodeTemp = node.left;
        if (leftNodeTemp == null) {
            return tree;
        }
        node.left = leftNodeTemp.right;
        leftNodeTemp.right = node;
        leftNodeTemp.parent = node.parent;
        node.parent = leftNodeTemp;
        if (leftNodeTemp.parent == null) {
            return leftNodeTemp;
        } else {
            return tree;
        }
    }

    /**
     * 找到合适的位置将节点插入树中
     */
    public static RedBlackNode rbInsert(RedBlackNode tree, RedBlackNode insertNode) {
        RedBlackNode insertIndex = tree;
        RedBlackNode insertIndexParent = insertIndex.parent;
        // 找到插入点
        while (insertIndex != null) {
            insertIndexParent = insertIndex;
            if (insertNode.value.compareTo(insertIndex.value) > 0) {
                insertIndex = insertIndex.right;
            } else {
                insertIndex = insertIndex.left;
            }
        }
        insertNode.parent = insertIndexParent;
        if (insertNode.value.compareTo(insertIndexParent.value) > 0) {
            insertIndexParent.right = insertNode;
        } else {
            insertIndexParent.left = insertNode;
        }
        insertNode.color = Color.RED;
        return rbInsertFixup(tree, insertNode);
    }

    /**
     * 保持树的红黑性
     */
    private static RedBlackNode rbInsertFixup(RedBlackNode tree, RedBlackNode insertNode) {
        while (insertNode.parent.color == Color.RED) {
            if (insertNode.parent == insertNode.parent.parent.left) {
                RedBlackNode temp = insertNode.parent.parent.right;
                if (temp.color == Color.RED) {
                    insertNode.parent.color = Color.BLACK;
                    temp.color = Color.BLACK;
                    insertNode.parent.parent.color = Color.RED;
                    temp = insertNode.parent.parent;
                } else if (insertNode == insertNode.parent.right) {
                    insertNode = insertNode.parent;
                    tree = leftRotate(tree, insertNode);
                }
                insertNode.parent.color = Color.BLACK;
                insertNode.parent.parent.color = Color.RED;
                tree = rightRotate(tree, insertNode);
            } else {
                RedBlackNode temp = insertNode.parent.parent.left;
                if (temp.color == Color.RED) {
                    insertNode.parent.color = Color.BLACK;
                    temp.color = Color.BLACK;
                    insertNode.parent.parent.color = Color.RED;
                    temp = insertNode.parent.parent;
                } else if (insertNode == insertNode.parent.left) {
                    insertNode = insertNode.parent;
                    tree = rightRotate(tree, insertNode);
                }
                insertNode.parent.color = Color.BLACK;
                insertNode.parent.parent.color = Color.RED;
                tree = leftRotate(tree, insertNode);
            }
        }
        return tree;
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
