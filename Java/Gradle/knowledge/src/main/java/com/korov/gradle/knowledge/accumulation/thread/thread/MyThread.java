package com.korov.gradle.knowledge.accumulation.thread.thread;

public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println(getName());
    }
}
