package org.algorithms.example.tree

import groovy.util.logging.Slf4j
import org.junit.jupiter.api.Test

@Slf4j
class TreeTest {
    @Test
    void printTree() {
        /**
         *        A
         *    B       C
         *  D       E    F
         * G H        I
         */
        TreeNode<String> root = null
        TreeNode<String> top = new TreeNode<>("D")
        TreeNode<String> left = new TreeNode<>("G")
        TreeNode<String> right = new TreeNode<>("H")
        top.left = left
        top.right = right
        left = top
        top = new TreeNode<>("B")
        top.left = left
        left = top
        top = new TreeNode<>("A")
        root = top
        right = new TreeNode<>("C")
        top.left = left
        top.right = right
        top = right
        left = new TreeNode<>("E")
        right = new TreeNode<>("F")
        top.left = left
        top.right = right
        top = left
        right = new TreeNode<>("I")
        top.right = right


        log.info("pre========================")
        // ABDGHCEIF
        TreeUtil.preorderTraversal(root)

        log.info("post=======================")
        // GHDBIEFCA
        TreeUtil.postorderTraversal(root)

        log.info("middle=====================")
        // GHDBIEFCA
        TreeUtil.inorderTraversal(root)
    }

    @Test
    void searchTest() {
        /**
         *        A
         *    B       C
         *  D       E    F
         * G H        I
         */
        TreeNode<String> root = null
        TreeNode<String> top = new TreeNode<>("D")
        TreeNode<String> left = new TreeNode<>("G")
        TreeNode<String> right = new TreeNode<>("H")
        top.left = left
        top.right = right
        left = top
        top = new TreeNode<>("B")
        top.left = left
        left = top
        top = new TreeNode<>("A")
        root = top
        right = new TreeNode<>("C")
        top.left = left
        top.right = right
        top = right
        left = new TreeNode<>("E")
        right = new TreeNode<>("F")
        top.left = left
        top.right = right
        top = left
        right = new TreeNode<>("I")
        top.right = right

        TreeNode<String> result = root.treeSearch(root, "C")
        log.info("value:{}", result == null ? "null" : result.getValue().toString())
    }
}
