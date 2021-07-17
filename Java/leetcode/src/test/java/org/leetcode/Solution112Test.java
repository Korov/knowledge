package org.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Stack;

class Solution112Test {

    @Test
    void hasPathSum() {

        Solution112.TreeNode root = null;

        Solution112.TreeNode left = new Solution112.TreeNode(7);
        Solution112.TreeNode right = new Solution112.TreeNode(2);
        Solution112.TreeNode parent = new Solution112.TreeNode(11, left, right);
        left = parent;
        parent = new Solution112.TreeNode(4, left, null);
        left = parent;
        right = new Solution112.TreeNode(8);
        root = new Solution112.TreeNode(5, left, right);
        parent = right;
        parent.left = new Solution112.TreeNode(13);
        parent.right = new Solution112.TreeNode(4);
        parent = parent.right;
        parent.right = new Solution112.TreeNode(1);


        Solution112 solution112 = new Solution112();
        Assertions.assertTrue(solution112.hasPathSum(root, 22));
    }

    @Test
    public void test() {

        Solution112.TreeNode left = new Solution112.TreeNode(2);
        Solution112.TreeNode right = new Solution112.TreeNode(3);
        Solution112.TreeNode parent = new Solution112.TreeNode(1, left, right);


        Solution112 solution112 = new Solution112();
        Assertions.assertFalse(solution112.hasPathSum(parent, 5));
    }
}