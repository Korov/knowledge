package org.algorithms.example.solution;

public class Solutions801 {
    public static int minSwap(int[] nums1, int[] nums2) {
        int[][] dp = new int[nums1.length][2];
        dp[0][0] = 0;
        dp[0][1] = 1;
        for (int i = 1; i < nums1.length; i++) {
            if ((nums1[i] > nums1[i - 1] && nums2[i] > nums2[i - 1]) && (nums1[i] > nums2[i - 1] && nums2[i] > nums1[i - 1])) {
                dp[i][0] = Math.min(dp[i - 1][0], dp[i - 1][1]);
                dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][1]) + 1;
            } else if (nums1[i] > nums1[i - 1] && nums2[i] > nums2[i - 1]) {
                dp[i][0] = dp[i - 1][0];
                dp[i][1] = dp[i - 1][1] + 1;
            } else {
                dp[i][0] = dp[i - 1][1];
                dp[i][1] = dp[i - 1][0] + 1;
            }
        }
        return Math.min(dp[nums1.length - 1][0], dp[nums1.length - 1][1]);
    }
}
