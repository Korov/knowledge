package com.korov.springboot.ThreadTest.Latch;

public class TestTask implements Runnable {
    @Override
    public void run() {
        System.out.printf("My name is:%s, Time is:%s.\n", Thread.currentThread().getName(), System.currentTimeMillis());
    }
}
