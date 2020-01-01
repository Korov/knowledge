package com.korov.springboot.ThreadTest.Latch;

import java.util.concurrent.CountDownLatch;

public class TestHarness {
    public long timeTasks(int threadNumber, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(threadNumber);
        for (int i = 0; i < threadNumber; i++) {
            Thread thread = new Thread() {
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }
        long start = System.nanoTime();
        startGate.countDown();
        System.out.printf("Task start\n");
        endGate.await();
        long end = System.nanoTime();
        System.out.printf("Time is: %s\n", end - start);
        return end - start;
    }
}
