package com.korov.gradle.knowledge.accumulation.Thread.synchronouscontainer.list;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListTest {
    private static final CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList();

    public static void main(String[] args) {
        ProductThread productThread = new ProductThread(list);
        ConsumerThread consumerThread = new ConsumerThread(list);
        Thread thread = new Thread(productThread);
        Thread thread1 = new Thread(consumerThread);
        thread.start();
        thread1.start();
    }
}

class ProductThread implements Runnable {
    private static CopyOnWriteArrayList<String> list;

    ProductThread(CopyOnWriteArrayList<String> list) {
        ProductThread.list = list;
    }

    @Override
    public void run() {
        while (true) {
            if (list.size() <= 20) {
                list.add(String.valueOf(Math.random()));
            }
        }
    }
}

class ConsumerThread implements Runnable {
    private static CopyOnWriteArrayList<String> list;

    ConsumerThread(CopyOnWriteArrayList<String> list) {
        ConsumerThread.list = list;
    }

    @Override
    public void run() {
        while (true) {
            if (list.size() >= 20) {
                System.out.println(list.remove(19) + ":" + list.size());
            }
        }
    }
}
