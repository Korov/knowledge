package com.korov.gradle.knowledge.accumulation.thread.saleticket;


public class ThreadGroupTest {
    public static void main(String[] args) {
        test1();
        groupTest();
    }

    private static void test1() {
        Thread testThread = new Thread(() -> {
            System.out.println("testThread Thread Group name:" +
                    Thread.currentThread().getThreadGroup().getName());
            System.out.println("testThread Thread name:" +
                    Thread.currentThread().getName());
            System.out.println("testThread Thread Priority:" +
                    Thread.currentThread().getPriority());
        });

        testThread.start();
        System.out.println("exex main:" + Thread.currentThread().getName());
    }

    private static void groupTest() {
        ThreadGroup threadGroup = new ThreadGroup("t1");
        threadGroup.setMaxPriority(6);
        Thread thread = new Thread(threadGroup, "thread");
        thread.setPriority(9);
        System.out.println("我是线程组的优先级" + threadGroup.getMaxPriority());
        System.out.println("我是线程的优先级" + thread.getPriority());
        System.out.println("我是线程的名称" + thread.getName());
    }
}
