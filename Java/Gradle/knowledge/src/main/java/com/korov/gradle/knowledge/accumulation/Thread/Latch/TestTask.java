package com.korov.gradle.knowledge.accumulation.Thread.Latch;

public class TestTask implements Runnable {
    @Override
    public void run() {
        System.out.printf("My name is:%s, Time is:%s.\n", Thread.currentThread().getName(), System.currentTimeMillis());
    }
}
