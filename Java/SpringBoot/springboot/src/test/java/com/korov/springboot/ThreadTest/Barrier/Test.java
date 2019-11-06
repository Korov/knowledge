package com.korov.springboot.ThreadTest.Barrier;

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
        ExecutorService executorpool = Executors.newFixedThreadPool(3);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        CycWorker work1 = new CycWorker(cyclicBarrier, "张三");
        CycWorker work2 = new CycWorker(cyclicBarrier, "李四");
        CycWorker work3 = new CycWorker(cyclicBarrier, "王五");

        executorpool.execute(work1);
        executorpool.execute(work2);
        executorpool.execute(work3);

        executorpool.shutdown();
    }
}
