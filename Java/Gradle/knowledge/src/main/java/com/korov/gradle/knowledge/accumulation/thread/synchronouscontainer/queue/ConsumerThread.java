package com.korov.gradle.knowledge.accumulation.thread.synchronouscontainer.queue;

import java.util.concurrent.BlockingQueue;

public class ConsumerThread implements Runnable {
    private static BlockingQueue<String> queue;

    ConsumerThread(BlockingQueue<String> queue) {
        ConsumerThread.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                queue.take();
                System.out.printf("Thread %s get vlue, queue size: %s \n", Thread.currentThread().getName(), queue.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
