package org.algorithms.example.solution;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/path-sum/
 */
public class Solutions112 {
    private static final Stack<TreeNode> stack = new Stack<>();

    private static final Map<TreeNode, Integer> treeMap = new HashMap<>();

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        } else if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }
        List<TreeNode> trees = new ArrayList<>();
        trees.add(root);
        treeMap.put(root, root.val);
        while (!trees.isEmpty()) {
            List<TreeNode> temp = new ArrayList<>();
            for (TreeNode treeNode : trees) {
                int parentSum = treeMap.get(treeNode);
                if (treeNode.left != null) {
                    temp.add(treeNode.left);
                    treeMap.put(treeNode.left, parentSum + treeNode.left.val);
                }
                if (treeNode.right != null) {
                    temp.add(treeNode.right);
                    treeMap.put(treeNode.right, parentSum + treeNode.right.val);
                }
                if (treeNode.left == null && treeNode.right == null && treeMap.get(treeNode) == targetSum) {
                    return true;
                }
            }
            trees = temp;
        }

        return false;
    }


    protected static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}


