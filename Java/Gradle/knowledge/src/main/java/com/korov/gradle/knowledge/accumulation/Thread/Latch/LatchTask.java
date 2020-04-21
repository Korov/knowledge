package com.korov.gradle.knowledge.accumulation.Thread.Latch;

import java.util.concurrent.CountDownLatch;

class LatchTask {
    static long timeTasks(int threadNumber, Runnable task) throws InterruptedException {
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch endGate = new CountDownLatch(threadNumber);
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
