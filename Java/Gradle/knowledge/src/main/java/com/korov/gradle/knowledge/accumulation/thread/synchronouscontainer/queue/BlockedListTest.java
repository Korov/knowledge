package com.korov.gradle.knowledge.accumulation.thread.synchronouscontainer.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockedListTest {
    private static final BlockingQueue<String> QUEUE = new ArrayBlockingQueue<>(20);

    public static void main(String[] args) {
        ProductThread productThread = new ProductThread(QUEUE);
        ConsumerThread consumerThread = new ConsumerThread(QUEUE);
        Thread thread = new Thread(productThread, "product thread0");
        Thread thread1 = new Thread(productThread, "product thread1");
        Thread thread2 = new Thread(productThread, "product thread2");
        Thread thread3 = new Thread(consumerThread, "consumer thread0");
        thread3.start();
        thread.start();
        thread1.start();
        thread2.start();
    }
}
