package org.algorithms.example.redblacktree

import org.junit.jupiter.api.Test

/**
 * @author zhu.lei
 * @date 2021-08-16 17:39
 */
class Demo {
    @Test
    void test() {
        RedBlackNode parent = new RedBlackNode(Color.BLACK, 5)
        RedBlackNode left = new RedBlackNode(Color.BLACK, 4)
        RedBlackNode right = new RedBlackNode(Color.BLACK, 6)
        parent.left = left
        parent.right = right

        RedBlackNode tree = parent

        tree = RedBlackUtil.leftRotate(tree, tree.right);

        tree = RedBlackUtil.rightRotate(tree, tree.left);
    }
}
