package com.korov.gradle.knowledge.accumulation.thread.cancell;

public class PrimeGeneratorMutiThreadTest {
    public static void main(String[] args) throws InterruptedException {
        PrimeGenerator generator = new PrimeGenerator();
        Thread thread = new Thread(generator);
        Thread thread1 = new Thread(generator);
        System.out.println("Thread state:" + thread.getState());
        System.out.println("Thread1 state:" + thread1.getState());
        thread.start();
        thread1.start();
        System.out.println("Thread state:" + thread.getState());
        System.out.println("Thread1 state:" + thread1.getState());
        Thread.sleep(500);
        generator.cancel();
        System.out.println("Thread state:" + thread.getState());
        System.out.println("Thread1 state:" + thread1.getState());
        Thread.sleep(1000);
        System.out.println("Thread state:" + thread.getState());
        System.out.println("Thread1 state:" + thread1.getState());
    }
}
