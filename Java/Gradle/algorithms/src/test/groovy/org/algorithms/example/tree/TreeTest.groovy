package org.algorithms.example.tree

import groovy.util.logging.Slf4j
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

@Slf4j
class TreeTest {
    /**
     *        E
     *    D       G
     *  B       F    I
     * A C         H
     */
    public static TreeNode<String> root = null

    @BeforeAll
    static void buildTree() {
        TreeNode<String> temp = new TreeNode<>()
        root = new TreeNode<>("E")

        root = temp.insert(root, new TreeNode<String>("D"))
        root = temp.insert(root, new TreeNode<String>("B"))
        root = temp.insert(root, new TreeNode<String>("C"))
        root = temp.insert(root, new TreeNode<String>("A"))
        root = temp.insert(root, new TreeNode<String>("G"))
        root = temp.insert(root, new TreeNode<String>("F"))
        root = temp.insert(root, new TreeNode<String>("I"))
        root = temp.insert(root, new TreeNode<String>("H"))
    }

    @Test
    void printTree() {
        // EDBACGFIH
        log.info("pre========================")
        TreeUtil.preorderTraversal(root)

        // ACBDFHIGE
        log.info("post=======================")
        TreeUtil.postorderTraversal(root)

        // ABCDEFGHI
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

    @Test
    void transplantTest() {
        TreeNode<String> helpNode = new TreeNode<>()
        TreeNode<String> root = new TreeNode<>("B")
        helpNode.insert(root, new TreeNode<String>("A"))
        helpNode.insert(root, new TreeNode<String>("C"))
        TreeNode<String> placedNode = root
        TreeNode<String> placeNode = new TreeNode<>("G")
        TreeUtil.inorderTraversal(root)
        root = helpNode.transplant(root, placedNode, placeNode)
        log.info("after========================")
        TreeUtil.inorderTraversal(root)
    }

    @Test
    void deleteTest() {
        TreeNode<String> helpNode = new TreeNode<>()
        TreeNode<String> root = new TreeNode<>("B")
        helpNode.insert(root, new TreeNode<String>("A"))
        helpNode.insert(root, new TreeNode<String>("C"))

        TreeNode<String> deleteNode
        log.info("delete C ====================")
        deleteNode = helpNode.treeSearch(root, "C")
        TreeUtil.inorderTraversal(root)
        root = helpNode.delete(root, deleteNode)
        log.info("after========================")
        TreeUtil.inorderTraversal(root)

        log.info("delete B ====================")
        helpNode.insert(root, new TreeNode<String>("C"))
        deleteNode = helpNode.treeSearch(root, "B")
        TreeUtil.inorderTraversal(root)
        root = helpNode.delete(root, deleteNode)
        log.info("after========================")
        TreeUtil.inorderTraversal(root)


        log.info("delete A ====================")
        helpNode.insert(root, new TreeNode<String>("B"))
        deleteNode = helpNode.treeSearch(root, "A")
        TreeUtil.inorderTraversal(root)
        root = helpNode.delete(root, deleteNode)
        log.info("after========================")
        TreeUtil.inorderTraversal(root)
    }

    @Test
    void deleteDTest() {
        TreeNode<String> helpNode = new TreeNode<>()
        TreeNode<String> root = new TreeNode<>("E")
        helpNode.insert(root, new TreeNode<String>("C"))
        helpNode.insert(root, new TreeNode<String>("D"))
        helpNode.insert(root, new TreeNode<String>("A"))
        helpNode.insert(root, new TreeNode<String>("B"))
        helpNode.insert(root, new TreeNode<String>("H"))
        helpNode.insert(root, new TreeNode<String>("F"))
        helpNode.insert(root, new TreeNode<String>("G"))
        helpNode.insert(root, new TreeNode<String>("K"))
        helpNode.insert(root, new TreeNode<String>("I"))
        helpNode.insert(root, new TreeNode<String>("J"))
        helpNode.insert(root, new TreeNode<String>("L"))

        TreeNode<String> deleteNode = helpNode.treeSearch(root, "H")
        TreeUtil.inorderTraversal(root)
        root = helpNode.delete(root, deleteNode)
        log.info("after========================")
        TreeUtil.inorderTraversal(root)
    }
}
