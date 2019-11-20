package com.korov.gradle.knowledge.accumulation.Thread.Barrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String[] args) {
        myTest();
    }

    public static void CellTest() {

    }

    private static void myTest() {
        ExecutorService executorpool = Executors.newFixedThreadPool(4);
        // parties表示打开这个栅栏需要的线程数量，这里需要四个线程才能打开这个栅栏
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4);

        CycWorker work1 = new CycWorker(cyclicBarrier, "张三");
        CycWorker work2 = new CycWorker(cyclicBarrier, "李四");
        CycWorker work3 = new CycWorker(cyclicBarrier, "王五");
        CycWorker work4 = new CycWorker(cyclicBarrier, "张六");

        executorpool.execute(work1);
        executorpool.execute(work2);
        executorpool.execute(work3);
        executorpool.execute(work4);

        executorpool.shutdown();
    }
}
