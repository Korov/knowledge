package com.korov.gradle.knowledge.accumulation.thread.thread;

public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
        MyThread thread = new MyThread();
        thread.start();
        thread.run();
        System.out.println("thread join start");
        thread.join(120000);
        System.out.println("thread join end");
        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().isInterrupted());
        System.out.println(Thread.interrupted());
        // 线程中断状态被interrupted重置为false
        System.out.println(Thread.currentThread().isInterrupted());
    }
}
