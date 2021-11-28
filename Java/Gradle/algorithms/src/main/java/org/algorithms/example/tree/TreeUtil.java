package org.algorithms.example.tree;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhu.lei
 * @date 2021-11-26 17:35
 */
@Slf4j
public class TreeUtil {
    /**
     * 中序遍历
     *
     * @param tree
     */
    public static void inorderTraversal(TreeNode tree) {
        if (tree == null) {
            return;
        }
        inorderTraversal(tree.getLeft());
        log.info(String.valueOf(tree.value));
        inorderTraversal(tree.getRight());
    }

    /**
     * 前序遍历
     *
     * @param tree
     */
    public static void preorderTraversal(TreeNode tree) {
        if (tree == null) {
            return;
        }
        log.info(String.valueOf(tree.value));
        preorderTraversal(tree.getLeft());
        preorderTraversal(tree.getRight());
    }

    /**
     * 前序遍历
     *
     * @param tree
     */
    public static void postorderTraversal(TreeNode tree) {
        if (tree == null) {
            return;
        }
        postorderTraversal(tree.getLeft());
        postorderTraversal(tree.getRight());
        log.info(String.valueOf(tree.value));
    }
}
