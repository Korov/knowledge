package com.korov.springboot.ThreadTest.Thread2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public class EventQueue {
    //最多存10个
    private final static int DEFAULT_MAX_EVENT = 10;
    private final int max;
    //定义队列用户存储事件
    private final LinkedList<Event> eventQueue = new LinkedList<>();
    private Logger logger = LoggerFactory.getLogger(EventQueue.class);

    public EventQueue() {
        this(DEFAULT_MAX_EVENT);
    }

    public EventQueue(int max) {
        this.max = max;
    }

    //提交一个event到队尾，如果队列满了，则提交的线程将会被阻塞
    public void offer(Event event) {
        synchronized (eventQueue) {
            while (eventQueue.size() >= max) {
                try {
                    logger.info("队列满了！");
                    eventQueue.wait();//队列满了则调用wait方法阻塞线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.info(Thread.currentThread().getName() + "任务已经提交！");
            eventQueue.addLast(event);
            eventQueue.notifyAll();//唤醒那些曾经执行monitor的wait方法而进入阻塞的线程
        }
    }

    //从队头获取数据，如果队列是空的那么获取数据的工作线程将会被阻塞
    public Event take() {
        synchronized (eventQueue) {
            while (eventQueue.isEmpty()) {
                try {
                    logger.info("队列是空的！");
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Event event = eventQueue.removeFirst();
            this.eventQueue.notifyAll();
            logger.info(Thread.currentThread().getName() + "事件：" + event + "已经在处理！");
            return event;
        }
    }

    static class Event {
    }

}
