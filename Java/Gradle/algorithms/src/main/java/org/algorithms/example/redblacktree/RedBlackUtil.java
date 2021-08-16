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
}
