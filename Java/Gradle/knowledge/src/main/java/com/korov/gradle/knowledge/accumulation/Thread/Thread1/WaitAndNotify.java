package com.korov.gradle.knowledge.accumulation.Thread.Thread1;

/**
 * 使用synchronized关键字实现同步
 * 该关键字只能用于方法和代码块
 */
public class WaitAndNotify implements Runnable {
    private final static int MAX = 50;
    private final static Object MUTEX = new Object();
    private int index = 1;

    public static void main(String[] args) {
        WaitAndNotify task = new WaitAndNotify();
        Thread ticketWindow1 = new Thread(task, "一号出号级");
        Thread ticketWindow2 = new Thread(task, "二号出号级");
        Thread ticketWindow3 = new Thread(task, "三号出号级");
        Thread ticketWindow4 = new Thread(task, "四号出号级");
        Thread ticketWindow5 = new Thread(task, "五号出号级");
        Thread ticketWindow6 = new Thread(task, "六号出号级");
        Thread ticketWindow7 = new Thread(task, "七号出号级");
        Thread ticketWindow8 = new Thread(task, "八号出号级");
        Thread ticketWindow9 = new Thread(task, "九号出号级");
        Thread ticketWindow10 = new Thread(task, "十号出号级");

        ticketWindow1.start();
        ticketWindow2.start();
        ticketWindow3.start();
        ticketWindow4.start();
        ticketWindow5.start();
        ticketWindow6.start();
        ticketWindow7.start();
        ticketWindow8.start();
        ticketWindow9.start();
        ticketWindow10.start();
    }

    @Override
    public void run() {
        // 某个线程获得这个锁之后就会一直买票，直到把票卖完，从头到尾只会有一个人在卖票
        while (index <= MAX) {
            int value = 0;
            synchronized (MUTEX) {
                value = index++;
                // 唤醒所有的线程
                MUTEX.notifyAll();
                // 释放所进入等待
                try {
                    if (value < MAX) {
                        MUTEX.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(50);
                System.out.println(Thread.currentThread().getName() + " 的号码是：" + value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
