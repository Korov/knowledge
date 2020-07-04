package org.leetcode;

/**
 * 给两个整数数组A和B，返回两个数组中公共的、长度最长的子数组的长度
 * <p>
 * 输入：
 * A:[1,2,3,2,1]
 * B:[3,2,1,4,7]
 * 输出：3
 * 最长公共字数组是[3, 2, 1]
 *
 * @author korov
 */
public class Solutions718 {
    public int findLength(int[] A, int[] B) {
        int i = 0, j = 0;
        int length = 1;
        int result = 0;
        for (; i < A.length; i++) {
            for (j = 0; j < B.length; j++) {
                if (A[i] == B[j]) {
                    while ((i + length < A.length) && (j + length < B.length) && (A[i + length] == B[j + length])) {
                        length++;
                    }
                    result = Math.max(result, length);
                    length = 1;
                }
            }
        }
        return result;
    }

    public int method2(int[] A, int[] B) {
        int n = A.length, m = B.length;
        int[][] dp = new int[n + 1][m + 1];
        int ans = 0;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                dp[i][j] = A[i] == B[j] ? dp[i + 1][j + 1] + 1 : 0;
                ans = Math.max(ans, dp[i][j]);
            }
        }
        return ans;
    }
}
