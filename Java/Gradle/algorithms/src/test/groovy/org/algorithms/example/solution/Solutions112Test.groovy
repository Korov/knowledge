package org.algorithms.example.solution;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Solutions112Test {

    @Test
    void hasPathSum() {

        Solutions112.TreeNode root = null;

        Solutions112.TreeNode left = new Solutions112.TreeNode(7);
        Solutions112.TreeNode right = new Solutions112.TreeNode(2);
        Solutions112.TreeNode parent = new Solutions112.TreeNode(11, left, right);
        left = parent;
        parent = new Solutions112.TreeNode(4, left, null);
        left = parent;
        right = new Solutions112.TreeNode(8);
        root = new Solutions112.TreeNode(5, left, right);
        parent = right;
        parent.left = new Solutions112.TreeNode(13);
        parent.right = new Solutions112.TreeNode(4);
        parent = parent.right;
        parent.right = new Solutions112.TreeNode(1);


        Solutions112 solution112 = new Solutions112();
        Assertions.assertTrue(Solutions112.hasPathSum(root, 22));
    }

    @Test
    public void test() {

        Solutions112.TreeNode left = new Solutions112.TreeNode(2);
        Solutions112.TreeNode right = new Solutions112.TreeNode(3);
        Solutions112.TreeNode parent = new Solutions112.TreeNode(1, left, right);


        Solutions112 solution112 = new Solutions112();
        Assertions.assertFalse(Solutions112.hasPathSum(parent, 5));
    }
}