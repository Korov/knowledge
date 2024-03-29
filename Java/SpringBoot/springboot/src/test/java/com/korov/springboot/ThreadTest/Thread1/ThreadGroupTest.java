package com.korov.springboot.ThreadTest.Thread1;

import org.junit.jupiter.api.Test;

public class ThreadGroupTest {
    public static void main(String[] args) {
        Thread testThread = new Thread(() -> {
            System.out.println("testThread当前线程组名字：" +
                    Thread.currentThread().getThreadGroup().getName());
            System.out.println("testThread线程名字：" +
                    Thread.currentThread().getName());
            System.out.println("testThread线程优先级：" +
                    Thread.currentThread().getPriority());
        });

        testThread.start();
        System.out.println("执行main方法线程名字：" + Thread.currentThread().getName());
    }

    @Test
    public void groupTest() {
        ThreadGroup threadGroup = new ThreadGroup("t1");
        threadGroup.setMaxPriority(6);
        Thread thread = new Thread(threadGroup, "thread");
        thread.setPriority(9);
        System.out.println("我是线程组的优先级" + threadGroup.getMaxPriority());
        System.out.println("我是线程的优先级" + thread.getPriority());
        System.out.println("我是线程的名称" + thread.getName());
    }
}
