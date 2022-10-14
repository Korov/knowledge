package org.algorithms.example.solution;

public class Solutions10 {
    public static boolean isMatch(String s, String p) {
        /*if (!p.contains("*") && s.length() != p.length()) {
            return false;
        }*/
        int index2 = 0;
        for (; index2 < p.length(); index2++) {
            if (p.charAt(index2) == '.' || p.charAt(index2) == s.charAt(0)) {
                break;
            }
        }
        if (index2 == p.length()) {
            return false;
        }
        boolean[][] dp = new boolean[s.length()][p.length()];
        dp[0][index2] = true;
        char preChar2 = p.charAt(index2);
        for (index2 = index2 + 1; index2 < p.length(); index2++) {
            for (int index1 = 1; index1 < s.length(); index1++) {
                if (p.charAt(index2) == '*') {
                    if (preChar2 == '.') {
                        dp[index1][index2] = dp[index1 - 1][index2 - 1];
                    } else if (preChar2 == s.charAt(index1)) {
                        dp[index1][index2] = dp[index1 - 1][index2 - 1];
                    }
                } else if (p.charAt(index2) == '.') {
                    dp[index1][index2] = dp[index1 - 1][index2 - 1];
                } else if (p.charAt(index2) == s.charAt(index1)) {
                    dp[index1][index2] = dp[index1 - 1][index2 - 1];
                }
            }
            preChar2 = p.charAt(index2);
        }
        return dp[s.length() - 1][p.length() - 1];
    }
}
