package com.korov.gradle.knowledge.accumulation.Thread.thread;

public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println(getName());
    }
}
