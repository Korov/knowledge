package com.korov.springboot.BaseTest;

public class HelloWorld {
    public static void main(String[] args) {
        String greeting = "Hello";
        int length = greeting.length(); // 5
        int cpCount = greeting.codePointCount(0, greeting.length()); // 5
        char first = greeting.charAt(0); // H
        int index = greeting.offsetByCodePoints(0, 2); // 2
        int cp = greeting.codePointAt(index); // 108
        System.out.println(length);
        System.out.println(cpCount);
        System.out.println(first);
        System.out.println(index);
        System.out.println(cp);
    }
}
