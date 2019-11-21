package com.korov.gradle.knowledge.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static boolean isFullMatch(String regex, String content) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }

    public static boolean isPartMatch(String regex, String content) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        return matcher.find();
    }

    public static String replace(String regex, String content, String replace) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        return matcher.replaceAll(replace);
    }

    public static List<String> finds(String regex, String content) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        List<String> matchers = new ArrayList<>(5);
        while (matcher.find()) {
            matchers.add(content.substring(matcher.start(), matcher.end()));
        }
        return matchers;
    }
}
