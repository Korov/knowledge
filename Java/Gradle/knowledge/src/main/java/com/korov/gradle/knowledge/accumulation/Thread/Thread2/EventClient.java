package com.korov.gradle.knowledge.accumulation.Thread.Thread2;

import java.util.concurrent.TimeUnit;

public class EventClient {
    public static void main(String[] args) {
        EventQueue eventQueue = new EventQueue();

        //提交时间
        new Thread(() -> {
            for (; ; ) {
                eventQueue.offer(new EventQueue.Event());//创建一个新线程不停的提交event
                try {
                    TimeUnit.MILLISECONDS.sleep(0);//处理时间花费时间10
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Producer").start();

        //处理事件
        new Thread(() -> {
            for (; ; ) {
                eventQueue.take();
                try {
                    TimeUnit.MILLISECONDS.sleep(10);//处理时间花费时间10
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Consumer").start();
    }


}
