package com.korov.gradle.knowledge;

public class MyTest {
    public static void main(String[] args) {
        String string = "abcedf";
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = string.toCharArray();
        for (int i = chars.length; i > 0; i--) {
            stringBuilder.append(chars[i - 1]);
        }
        System.out.println(stringBuilder.toString());
    }
}
