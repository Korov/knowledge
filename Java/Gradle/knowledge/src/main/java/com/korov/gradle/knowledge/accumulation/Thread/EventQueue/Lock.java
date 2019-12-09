package com.korov.gradle.knowledge.accumulation.Thread.EventQueue;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * 构造显示的BooleanLock，使其具备synchronized关键字所有功能的同时又具备可中断和lock超时功能
 */
public interface Lock {
    void lock() throws InterruptedException;

    void lock(long mills) throws InterruptedException, TimeoutException;

    void unlock();

    List<Thread> getBlockedThreads();
}
