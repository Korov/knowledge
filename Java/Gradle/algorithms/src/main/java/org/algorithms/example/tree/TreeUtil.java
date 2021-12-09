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
    public static <E extends Comparable<E>> void inorderTraversal(TreeNode<E> tree) {
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
    public static <E extends Comparable<E>> void preorderTraversal(TreeNode<E> tree) {
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
    public static <E extends Comparable<E>> void postorderTraversal(TreeNode<E> tree) {
        if (tree == null) {
            return;
        }
        postorderTraversal(tree.getLeft());
        postorderTraversal(tree.getRight());
        log.info(String.valueOf(tree.value));
    }
}
