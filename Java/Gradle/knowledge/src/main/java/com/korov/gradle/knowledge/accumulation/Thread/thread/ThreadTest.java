package com.korov.gradle.knowledge.accumulation.Thread.thread;

public class ThreadTest {
    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();
        thread.run();
    }
}
