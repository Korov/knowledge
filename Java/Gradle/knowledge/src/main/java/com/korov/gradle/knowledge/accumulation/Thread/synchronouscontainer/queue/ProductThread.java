package com.korov.gradle.knowledge.accumulation.Thread.synchronouscontainer.queue;

import java.util.concurrent.BlockingQueue;

public class ProductThread implements Runnable {
    private static BlockingQueue<String> queue;

    ProductThread(BlockingQueue<String> queue) {
        ProductThread.queue = queue;
    }

    @Override
    public void run() {
        String value;
        while (true) {
            value = String.format("Thread: %s, value: %s", Thread.currentThread().getName(), Math.random());
            try {
                queue.put(value);
            } catch (InterruptedException e) {
                System.out.printf("Thread %s Interrupted\n", Thread.currentThread().getName());
            }
            System.out.printf("Thread %s add vlue, size :%s\n", Thread.currentThread().getName(), queue.size());
        }
    }
}
