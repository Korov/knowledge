package com.korov.gradle.knowledge.accumulation.thread.Barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CycWorker implements Runnable {
    private final CyclicBarrier barrier;
    private final String name;
    private int time = 1;

    CycWorker(CyclicBarrier barrier, String name) {
        this.barrier = barrier;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name + " is working!" + getTime());
        try {
            Thread.sleep(500);
            System.out.println(name + " work is done!");
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(name + " work again!");
    }

    private synchronized String getTime() {
        String timeValue = String.valueOf(time);
        time++;
        return timeValue;
    }
}
