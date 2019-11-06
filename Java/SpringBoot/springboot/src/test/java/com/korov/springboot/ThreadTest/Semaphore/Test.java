package com.korov.springboot.ThreadTest.Semaphore;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        BoundedHashSet<String> set = new BoundedHashSet(5);
        set.add("aaa");
        set.add("aaa1");
        set.add("aaa2");
        set.add("aaa3");
        set.add("aaa4");
        set.add("aaa5");
        System.out.printf("debug\n");
    }
}
