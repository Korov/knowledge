package org.leetcode;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/path-sum/
 */
public class Solution112 {
    private static final Stack<TreeNode> stack = new Stack<>();

    public boolean hasPathSum(TreeNode root, int targetSum) {
        int sum = root.val;
        if (root.left == null && root.right == null) {
            return sum == targetSum;
        }
        stack.add(root);
        while (stack.size() > 1 && (stack.peek().left != null || stack.peek().right != null)) {
            TreeNode topNode = stack.peek();

            if (topNode.left == null && topNode.right == null) {
                if (sum == targetSum) {
                    return true;
                } else {
                    sum -= topNode.val;
                    stack.pop();
                    TreeNode topParent = stack.peek();
                    if (topParent.left == topNode) {
                        topParent.left = null;
                    } else if (topParent.right == topNode) {
                        topParent.right = null;
                    }
                }
            } else if (topNode.left != null) {
                stack.add(topNode.left);
                sum += topNode.left.val;
            } else {
                stack.add(topNode.right);
                sum += topNode.right.val;
            }
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


