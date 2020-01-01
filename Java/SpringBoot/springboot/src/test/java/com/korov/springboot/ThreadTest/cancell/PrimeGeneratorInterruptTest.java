package com.korov.springboot.ThreadTest.cancell;

public class PrimeGeneratorInterruptTest {
    public static void main(String[] args) throws InterruptedException {
        PrimeGenerator generator = new PrimeGenerator();
        Thread thread = new Thread(generator);
        System.out.println("Thread state:" + thread.getState());
        thread.start();
        System.out.println("Thread state:" + thread.getState());
        System.out.println("Thread isInterrupted:" + thread.isInterrupted());

        System.out.println("Thread state:" + thread.getState());
        thread.interrupt();
        System.out.println("Thread isInterrupted:" + thread.isInterrupted());
        System.out.println("Thread state:" + thread.getState());
        Thread.sleep(500);
        System.out.println("Thread state:" + thread.getState());
    }
}
