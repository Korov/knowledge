package com.korov.gradle.knowledge.accumulation.Thread.Communication;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * 生产者消费者模型，通过信号量实现
 * 初始信号量设置为0,生产者生产一个数据之后信号量加1
 * 消费者消费一个数据之后信号量减一
 */
public class SemaphoreDemo {
    private static Semaphore semaphore = new Semaphore(0);
    private static volatile Queue<String> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        Thread provider = new Thread(new Provider());
        Thread consumer = new Thread(new Consumer());
        provider.start();
        consumer.start();
    }

    /**
     * 生产者
     */
    public static class Provider implements Runnable {
        @Override
        public void run() {
            provider();
        }
    }

    public static void provider() {
        for (int i = 0; ; i++) {

            //生成一个产品，放到队列里
            String p = String.format("product_%d", i);
            queue.offer(p);
            System.out.println(String.format("Send sig >>>>>>>>>>>>> %s", p));

            //发出信号量
            semaphore.release();
        }
    }

    /**
     * 消费者
     */
    public static class Consumer implements Runnable {
        @Override
        public void run() {
            consume();
        }
    }

    public static void consume() {
        while (true) {
            //等待信号量
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //从队列里取出一个产品
            String s = queue.poll();
            System.out.println(String.format("Get sig : %s", s));
        }
    }
}
