package org.algorithms.example.redblacktree

import groovy.util.logging.Slf4j
import org.junit.jupiter.api.Test

@Slf4j
class Demo {
    @Test
    void rotateTest() {
        RedBlackNode parent = new RedBlackNode(Color.BLACK, 5)
        RedBlackNode left = new RedBlackNode(Color.BLACK, 4)
        left.parent = parent
        RedBlackNode right = new RedBlackNode(Color.BLACK, 6)
        right.parent = parent
        parent.left = left
        parent.right = right

        RedBlackNode tree = parent

        tree = RedBlackUtil.leftRotate(tree, tree)

        tree = RedBlackUtil.rightRotate(tree, tree)
        log.info("debug")

        tree = RedBlackUtil.leftRotate(tree, tree.right);
        log.info("debug")
    }

    @Test
    void insertTest(){
        RedBlackNode parent = new RedBlackNode(Color.BLACK, 5)
        RedBlackNode left = new RedBlackNode(Color.BLACK, 4)
        left.parent = parent
        RedBlackNode right = new RedBlackNode(Color.BLACK, 6)
        right.parent = parent
        parent.left = left
        parent.right = right

        RedBlackNode tree = parent

        RedBlackNode insertNode = new RedBlackNode(Color.BLACK, 3)
        tree = RedBlackUtil.rbInsert(tree, insertNode)
        insertNode = new RedBlackNode(Color.BLACK, 2)
        tree = RedBlackUtil.rbInsert(tree, insertNode)
        log.info("debug")
    }
}
