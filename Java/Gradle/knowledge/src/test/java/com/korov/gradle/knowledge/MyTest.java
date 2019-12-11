package com.korov.gradle.knowledge;

import com.korov.gradle.knowledge.utils.RegexUtil;

import java.util.List;

public class MyTest {
    public static void main(String[] args) {
        String string = "abcedf";
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = string.toCharArray();
        for (int i = chars.length; i > 0; i--) {
            stringBuilder.append(chars[i - 1]);
        }
        System.out.println(stringBuilder.toString());

        List<String> values = RegexUtil.finds("No\\.\\d+", "fefNo.13,,No.46");
        values.forEach(System.out::println);
    }
}
