package com.korov.gradle.knowledge.accumulation.Thread.EventQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.currentThread;

/**
 * 通过控制一个Boolean变量的开关来决定是否允许当前的线程获取该锁
 */
public class BooleanLock implements Lock {

    private final List<Thread> blockedList = new ArrayList<>();//存储哪些线程在获取当前线程时进入了阻塞状态
    private Thread currentThread;//当前拥有锁的线程
    private boolean locked = false;//true代表该锁已经被某个线程获得，该线程就是currentThread，false代表当前该锁没有被任何线程获得或者已经被释放

    @Override
    public void lock() throws InterruptedException {
        synchronized (this) {
            while (locked) {
                Thread tempThread = currentThread();
                try {
                    if (!blockedList.contains(tempThread)) {
                        blockedList.add(tempThread);
                    }
                    wait();
                } catch (InterruptedException e) {
                    blockedList.remove(tempThread);
                    throw e;
                }
            }
        }
        blockedList.remove(currentThread());
        locked = true;
        currentThread = currentThread();
    }


    @Override
    public void lock(long mills) throws InterruptedException, TimeoutException {
        synchronized (this) {
            if (mills <= 0) {
                lock();
            } else {
                long remainingMills = mills;
                long endMills = currentTimeMillis() + remainingMills;
                while (locked) {
                    if (remainingMills <= 0) {
                        throw new TimeoutException("can not get the lock during " + mills + "ms.");
                    }
                    if (!blockedList.contains(currentThread())) {
                        blockedList.add(currentThread());
                    }
                    wait(remainingMills);
                    remainingMills = endMills - currentTimeMillis();
                }
                locked = true;
                currentThread = currentThread();
            }
        }
    }

    @Override
    public void unlock() {
        synchronized (this) {
            if (currentThread == currentThread()) {
                locked = false;
                Optional.of(currentThread().getName() + " release the monitor.").ifPresent(System.out::println);
                notifyAll();
            }
        }
    }

    @Override
    public List<Thread> getBlockedThreads() {
        return Collections.unmodifiableList(blockedList);
    }
}
