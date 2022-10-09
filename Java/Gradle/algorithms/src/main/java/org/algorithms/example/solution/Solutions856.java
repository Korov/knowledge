package org.algorithms.example.solution;

public class Solutions856 {
    public static int scoreOfParentheses(String s) {
        int[] stack = new int[s.length() / 2];
        int index = 0;
        byte[] array = s.getBytes();
        stack[0] = -1;
        for (int i = 1; i < array.length; i++) {
            if (array[i] == 40) {
                stack[++index] = -1;
            } else {
                if (stack[index] == -1) {
                    if (index > 0 && stack[index - 1] > 0) {
                        stack[index - 1] = stack[index - 1] + 1;
                        index--;
                    } else {
                        stack[index] = 1;
                    }
                } else {
                    if (index > 0) {
                        stack[index - 1] = 2 * stack[index];
                        index--;
                    }
                    if (index > 0 && stack[index - 1] > 0) {
                        stack[index - 1] = stack[index - 1] + stack[index];
                        index--;
                    }
                }
            }
        }
        return stack[0];
    }
}
