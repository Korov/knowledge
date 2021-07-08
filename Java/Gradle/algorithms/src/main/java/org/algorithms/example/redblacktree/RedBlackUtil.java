package org.algorithms.example.redblacktree;

/**
 * @author korov
 */
public class RedBlackUtil {
    /**
     * 对node进行左旋
     */
    public static void leftRotate(RedBlockNode tree, RedBlockNode node) {
        RedBlockNode temp = node.right;
        node.right = temp.left;
        temp.left = node;
        temp.parent = node.parent;
        node.parent = temp;
    }
}
