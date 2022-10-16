package org.algorithms.example.solution;

public class Solutions10 {
    public static boolean isMatch(String s, String p) {
        // 这里之所以length + 1是因为害怕出现 s=''  p=a* 的情况，所以默认在s和p的前面都加上一个' '
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;
        // 因为p的第一位不可能是*，而p要想和 ' ' 必须是所有字母都出现0次，例如 s=' ', p='a*b*', [true, false, true, false, true]
        for (int i = 1; i < p.length(); i++) {
            if (p.charAt(i) == '*') {
                dp[0][i + 1] = dp[0][i - 1];
            }
        }

        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < p.length(); j++) {
                if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.') {
                    dp[i + 1][j + 1] = dp[i][j];
                } else if (p.charAt(j) == '*') {
                    if (dp[i + 1][j - 1]) {
                        // 此处表示* 出现0次，因此直接丢掉，所以是j往前移动两位
                        dp[i + 1][j + 1] = dp[i + 1][j - 1];
                    } else if (s.charAt(i) == p.charAt(j - 1) || p.charAt(j - 1) == '.') {
                        // 其实这里应该是dp[i + 1][j + 1] = dp[i][j]; 但是 a* 应该被当做整体来看，所以此时的 dp[i][j] = dp[i][j + 1]
                        dp[i + 1][j + 1] = dp[i][j + 1];
                    }
                }
            }
        }
        return dp[s.length()][p.length()];
    }
}
