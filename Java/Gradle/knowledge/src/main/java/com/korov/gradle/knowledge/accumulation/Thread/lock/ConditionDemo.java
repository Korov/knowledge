package com.korov.gradle.knowledge.accumulation.Thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {
    /**
     * 设置一个重入锁
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 获得指定Lock对象对应的Condition
     */
    private final Condition cond = lock.newCondition();

    private String demoValue = "";

    public void setDemoVelue(String value) {
        lock.lock();
        try {
            while (!demoValue.equals("")) {
                System.out.println("wait to empty value");
                cond.await();
            }
            demoValue = value;
            System.out.println("get the value :" + demoValue);
            cond.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public String getDemoValue() {
        lock.lock();
        try {
            while (demoValue.equals("")) {
                System.out.println("wait to give value");
                cond.await();
            }
            cond.signalAll();
            return demoValue;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        System.out.println("fail to get value");
        return null;
    }
}
