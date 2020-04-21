package com.korov.gradle.knowledge.accumulation.Thread.saleticket;

public class ObjectLock {
    private static int value = 0;

    static class MyThread implements Runnable {
        @Override
        public void run() {
            while (ObjectLock.value < 100) {
                System.out.println(java.lang.Thread.currentThread().getName() + ": " + ObjectLock.value++);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new MyThread(), "MyThread1").start();
        new Thread(new MyThread(), "MyThread2").start();
        new Thread(new MyThread(), "MyThread3").start();
        new Thread(new MyThread(), "MyThread4").start();
    }
}
