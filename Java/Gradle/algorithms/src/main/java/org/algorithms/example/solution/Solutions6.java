package org.algorithms.example.solution;

public class Solutions6 {
    public static String convert(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }
        int gap = 2 * numRows - 2;
        int[] heads = new int[(s.length() / gap) + (s.length() % gap == 0 ? 0 : 1)];
        int j = 0;
        for (int i = 0; i < s.length(); i += gap) {
            heads[j++] = i;
        }
        StringBuilder stringBuilder = new StringBuilder();
        // 按行读取数据
        for (int row = 1; row <= numRows; row++) {
            // 首行和尾行都是只有一个数据
            if (row == 1) {
                for (int head : heads) {
                    stringBuilder.append(s.charAt(head));
                }
            } else if (row == numRows) {
                for (int head : heads) {
                    if (head + row <= s.length()) {
                        stringBuilder.append(s.charAt(head + row - 1));
                    }
                }
            } else {
                // 中间行有两个数据，每行数据的间隔不同
                gap = 2 * (numRows - row);
                for (int head : heads) {
                    if (head + row <= s.length()) {
                        stringBuilder.append(s.charAt(head + row - 1));
                    }
                    if (head + row + gap <= s.length()) {
                        stringBuilder.append(s.charAt(head + row + gap - 1));
                    }
                }
            }
        }
        return stringBuilder.toString();
    }
}
