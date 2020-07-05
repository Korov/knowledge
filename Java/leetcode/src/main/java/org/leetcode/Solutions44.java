package org.leetcode;

/**
 * 给定一个字符串（s）和一个字符模式（P），实现一个支持 ？ 和 * 的通配符匹配
 * ？ 可以匹配任何单个字符
 * * 可以匹配任意字符串（包括空字符串）
 * 两个字符串完全匹配才算匹配成功
 *
 * @author korov
 * @date 2020/7/5
 */
public class Solutions44 {
    public boolean method1(String s, String p) {
        char[] s1 = s.toCharArray();
        char[] p1 = p.toCharArray();
        int index1 = 0, index2 = 0;
        while (index1 < s1.length || index2 < p1.length) {
            if (p1[index1] == '*') {
                if (index1 == s1.length) {
                    return true;
                } else if (s1[index1 + 1] != p1[index2]) {
                    index2++;
                }
            } else if (p1[index1] == '*' && s1[index1 + 1] == p1[index2]) {
                index1++;
                index2++;
            } else if (p1[index1] == '?') {
                index1++;
                index2++;
            } else if (s1[index1] == p1[index2]) {
                index1++;
                index2++;
            }
        }
        return true;
    }

    public boolean method2(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int i = 1; i <= n; ++i) {
            if (p.charAt(i - 1) == '*') {
                dp[0][i] = true;
            } else {
                break;
            }
        }

        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else if (p.charAt(j - 1) == '?' || s.charAt(i - 1) == p.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }
        return dp[m][n];
    }

    public boolean method3(String s, String p) {
        int sRight = s.length(), pRight = p.length();
        while (sRight > 0 && pRight > 0 && p.charAt(pRight - 1) != '*') {
            if (charMatch(s.charAt(sRight - 1), p.charAt(pRight - 1))) {
                --sRight;
                --pRight;
            } else {
                return false;
            }
        }

        if (pRight == 0) {
            return sRight == 0;
        }

        int sIndex = 0, pIndex = 0;
        int sRecord = -1, pRecord = -1;

        while (sIndex < sRight && pIndex < pRight) {
            if (p.charAt(pIndex) == '*') {
                ++pIndex;
                sRecord = sIndex;
                pRecord = pIndex;
            } else if (charMatch(s.charAt(sIndex), p.charAt(pIndex))) {
                ++sIndex;
                ++pIndex;
            } else if (sRecord != -1 && sRecord + 1 < sRight) {
                ++sRecord;
                sIndex = sRecord;
                pIndex = pRecord;
            } else {
                return false;
            }
        }

        return allStars(p, pIndex, pRight);
    }

    public boolean allStars(String str, int left, int right) {
        for (int i = left; i < right; ++i) {
            if (str.charAt(i) != '*') {
                return false;
            }
        }
        return true;
    }

    public boolean charMatch(char u, char v) {
        return u == v || v == '?';
    }
}
