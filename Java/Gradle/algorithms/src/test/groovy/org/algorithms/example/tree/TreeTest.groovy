package org.algorithms.example.tree

import groovy.util.logging.Slf4j
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

@Slf4j
class TreeTest {
    /**
     *        A
     *    B       C
     *  D       E    F
     * G H        I
     */
    public static TreeNode<String> root = null

    @BeforeAll
    static void buildTree() {
        TreeNode<String> temp = new TreeNode<>("D")
        root = new TreeNode<>("E")

        root = temp.insert(root, new TreeNode<String>("A"))
        root = temp.insert(root, new TreeNode<String>("B"))
        root = temp.insert(root, new TreeNode<String>("C"))
        root = temp.insert(root, new TreeNode<String>("D"))
        root = temp.insert(root, new TreeNode<String>("F"))
        root = temp.insert(root, new TreeNode<String>("G"))
        root = temp.insert(root, new TreeNode<String>("H"))
        root = temp.insert(root, new TreeNode<String>("I"))
    }

    @Test
    void printTree() {
        log.info("pre========================")
        TreeUtil.preorderTraversal(root)

        log.info("post=======================")
        TreeUtil.postorderTraversal(root)

        log.info("middle=====================")
        TreeUtil.inorderTraversal(root)
    }

    @Test
    void searchTest() {
        TreeNode<String> result = root.treeSearch(root, "C")
        log.info("value:{}", result == null ? "null" : result.getValue().toString())

        result = root.treeSearch(root, "CD")
        log.info("value:{}", result == null ? "null" : result.getValue().toString())
    }

    @Test
    void maxAndMinTest() {
        log.info("max:{}", root.max(root).getValue())
        log.info("min:{}", root.min(root).getValue())
    }

    @Test
    void insertTest() {
        TreeNode<String> temp = new TreeNode<>()
        TreeNode<String> insertRoot
        insertRoot = temp.insert(insertRoot, new TreeNode<String>("A"))
        TreeUtil.preorderTraversal(insertRoot)
    }

    @Test
    void successorAndPredecessorNodeTest() {
        TreeNode<String> node = root.treeSearch(root, "D")
        TreeNode<String> successorNode = node.successor(node)
        log.info("node:{}, successor:{}", node.getValue(), successorNode.getValue())
        TreeNode<String> predecessorNode = node.predecessor(node)
        log.info("node:{}, predecessor:{}", node.getValue(), predecessorNode.getValue())
    }
}
