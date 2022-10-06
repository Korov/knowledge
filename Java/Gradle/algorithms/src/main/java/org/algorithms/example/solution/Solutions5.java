package org.algorithms.example.solution;

import java.util.Objects;

public class Solutions5 {
    public static String longestPalindrome(String s) {
        Boolean[][] range = new Boolean[s.length()][s.length()];

        String maxString = s.substring(0, 1);
        for (int i = 1; i < s.length(); i++) {
            for (int j = i; j >= 0; j--) {
                range[j][i] = getRange(s, range, j, i);
                if (Objects.equals(range[j][i], true)) {
                    if (maxString.length() < (i - j + 1)) {
                        maxString = s.substring(j, i + 1);
                    }
                }
            }
        }
        return maxString;
    }

    private static boolean getRange(String s, Boolean[][] range, int index1, int index2) {
        if (index1 == index2) {
            return true;
        } else if (!s.substring(index1, index1 + 1).equals(s.substring(index2, index2 + 1))) {
            return false;
        } else if (s.substring(index1, index1 + 1).equals(s.substring(index2, index2 + 1)) && index2 - 1 == index1) {
            return true;
        }
        if (range[index1][index2] == null) {
            return getRange(s, range, index1 + 1, index2 - 1);
        } else {
            return range[index1][index2];
        }
    }
}
