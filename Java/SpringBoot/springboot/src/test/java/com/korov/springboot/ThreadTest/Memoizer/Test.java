package com.korov.springboot.ThreadTest.Memoizer;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Computable<String, Integer> compute = new ComputableImpl<>();
        Memoizer<String, Integer> memoizer = new Memoizer<>(compute);
        long startTime = System.currentTimeMillis();
        memoizer.compute("30");
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        startTime = System.currentTimeMillis();
        memoizer.compute("30");
        endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        startTime = System.currentTimeMillis();
        memoizer.compute("31");
        endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
}
