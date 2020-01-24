package com.korov.gradle.knowledge.accumulation.Thread.Executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorDemo {
    public static void main(String[] args) {

        createThreadWithCoreNum();
        createThreadWithMaxNum();
        createThreadWithReject();

    }

    private static void createThreadWithReject() {

        ExecutorService executor = new ThreadPoolExecutor(5, 10, 3000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(30));
        //此方法会发生拒绝异常，因为阻塞队列满了之后，有最大线程数的时候仍然处理不了
        for (int i = 0; i < 50; i++) {
            int temp = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(String.valueOf(temp) + " : " + Thread.currentThread().getName());
                }
            });
        }
        executor.shutdown();
    }

    private static void createThreadWithMaxNum() {
        ExecutorService executor = new ThreadPoolExecutor(5, 10, 3000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(30));
        //此处只会创建10个线程，因为核心线程数5个满了之后，阻塞队列也会满，就需要创建更多的线程来处理数据
        for (int i = 0; i < 40; i++) {
            int temp = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(String.valueOf(temp) + " : " + Thread.currentThread().getName());
                }
            });
        }
        executor.shutdown();
    }

    private static void createThreadWithCoreNum() {
        ExecutorService executor = new ThreadPoolExecutor(5, 10, 3000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(30));
        //此处只会创建5个线程，因为核心线程数就是5，且阻塞队列未满
        for (int i = 0; i < 10; i++) {
            int temp = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("createThreadWithCoreNum" + String.valueOf(temp) + " : " + Thread.currentThread().getName());
                }
            });
        }
        executor.shutdown();
    }
}
