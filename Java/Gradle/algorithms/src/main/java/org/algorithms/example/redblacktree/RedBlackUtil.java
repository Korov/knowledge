package org.algorithms.example.redblacktree;

/**
 * @author korov
 */
public class RedBlackUtil {
    /**
     * 对node进行左旋
     */
    public static RedBlackNode leftRotate(RedBlackNode tree, RedBlackNode node) {
        RedBlackNode rightNodeTemp = node.right;
        if (rightNodeTemp == null) {
            return tree;
        }
        node.right = rightNodeTemp.left;
        rightNodeTemp.left = node;
        rightNodeTemp.parent = node.parent;
        node.parent = rightNodeTemp;
        if (rightNodeTemp.parent == null) {
            return rightNodeTemp;
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
                rightRotate(tree, insertNode);
            } else {
                // TODO
            }
        }
        return null;
    }
}
