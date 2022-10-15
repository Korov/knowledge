package org.algorithms.example.solution;

public class Solutions10 {
    public static boolean isMatch(String s, String p) {
        char a, b;
        int index1 = 0, index2 = 0;
        boolean isMultiple = false;
        while (index1 < s.length() && index2 < p.length()) {
            a = s.charAt(index1);
            if (!isMultiple && index2 < p.length() - 1 && p.charAt(index2 + 1) == '*') {
                isMultiple = true;
                index2 = index2 + 1;
            } else {
                isMultiple = false;
            }
            if (isMultiple) {
                b = p.charAt(index2 - 1);
            } else {
                b = p.charAt(index2);
            }
            if (a == b || b == '.') {
                if (isMultiple) {
                    index1++;
                } else {
                    index1++;
                    index2++;
                }
            }
        }
        return (index1 == (s.length() - 1)) && (index2 == (p.length() - 1));
    }

    public static boolean matches(String s, String p, int i, int j) {
        if (i == 0) {
            return false;
        }
        if (p.charAt(j - 1) == '.') {
            return true;
        }
        return s.charAt(i - 1) == p.charAt(j - 1);
    }

    public static boolean isMatch1(String s, String p) {
        int m = s.length();
        int n = p.length();

        boolean[][] f = new boolean[m + 1][n + 1];
        f[0][0] = true;
        for (int i = 0; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (p.charAt(j - 1) == '*') {
                    f[i][j] = f[i][j - 2];
                    if (matches(s, p, i, j - 1)) {
                        f[i][j] = f[i][j] || f[i - 1][j];
                    }
                } else {
                    if (matches(s, p, i, j)) {
                        f[i][j] = f[i - 1][j - 1];
                    }
                }
            }
        }
        return f[m][n];
    }
}
