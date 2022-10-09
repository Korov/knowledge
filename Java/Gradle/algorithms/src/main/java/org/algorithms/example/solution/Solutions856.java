package org.algorithms.example.solution;

public class Solutions856 {
    public static int scoreOfParentheses(String s) {
        Object[] stack = new Object[s.length() / 2];
        int index = 0;
        char[] array = s.toCharArray();
        stack[0] = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] == '(') {
                stack[++index] = array[i];
            } else {
                if (stack[index] instanceof Character) {
                    if (index > 0 && stack[index - 1] instanceof Integer) {
                        stack[index - 1] = (int) stack[index - 1] + 1;
                        index--;
                    } else {
                        stack[index] = 1;
                    }
                } else {
                    if (index > 0) {
                        stack[index - 1] = 2 * (int) stack[index];
                        index--;
                    }
                    if (index > 0 && stack[index - 1] instanceof Integer) {
                        stack[index - 1] = (int) stack[index - 1] + (int) stack[index];
                        index--;
                    }
                }
            }
        }
        return (int) stack[0];
    }
}
