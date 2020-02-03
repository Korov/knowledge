package com.distributed.lock.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通过lock实现多个线程之间的锁
 */
public class LockTest {
    static class MyThread implements Runnable {
        private final String value;
        private final Lock lock;

        public MyThread(String value, Lock lock) {
            this.value = value;
            this.lock = lock;
        }

        @Override
        public void run() {
            char[] values = value.toCharArray();
            lock.lock();
            try {
                for (int i = 0; i < values.length; i++) {
                    System.out.print(values[i]);
                }
                System.out.println("|||");
            } finally {
                lock.unlock();
            }

        }
    }

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Thread thread1 = new Thread(new MyThread("zhangsan", reentrantLock), "thread1");
        Thread thread2 = new Thread(new MyThread("lisi", reentrantLock), "thread1");
        Thread thread3 = new Thread(new MyThread("wangwu", reentrantLock), "thread1");
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
