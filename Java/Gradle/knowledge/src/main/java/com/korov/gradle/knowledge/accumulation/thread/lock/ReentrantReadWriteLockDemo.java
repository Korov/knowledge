package com.korov.gradle.knowledge.accumulation.thread.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDemo {
    //静态内部类实现线程共享
    static class Example {
        //创建lock
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        //读操作
        public void read() {
            //获取读锁并上锁
            lock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + "读线程开始");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "读线程结束");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //解锁
                lock.readLock().unlock();
            }
        }

        //写操作
        public void write() {
            //获取写锁并上锁
            lock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + "写线程开始");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "写线程结束");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //解锁
                lock.writeLock().unlock();
            }
        }
    }

    public static void main(String[] args) {
        final Example example = new Example();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    example.read();
                    example.write();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    example.read();
                    example.write();
                }
            }
        }).start();
    }
}
