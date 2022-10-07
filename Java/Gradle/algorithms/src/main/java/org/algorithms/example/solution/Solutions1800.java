package org.algorithms.example.solution;

public class Solutions1800 {
    public static int maxAscendingSum(int[] nums) {
        int sum = nums[0];
        int maxSum = sum;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] < nums[i]) {
                sum += nums[i];
            } else {
                if (maxSum < sum) {
                    maxSum = sum;
                }
                sum = nums[i];
            }
        }
        return Math.max(maxSum, sum);
    }

    public static void main(String[] args) {
        int[] nums = new int[]{3, 6, 10, 1, 8, 9, 9, 8, 9};
        System.out.println(maxAscendingSum(nums));
    }
}
