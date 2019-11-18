package com.korov.gradle.knowledge.accumulation.Thread.cancell;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.korov.gradle.knowledge.accumulation.Thread.cancell.TimedRun.timedRun;


public class TimedRunTest {
    public static void main(String[] args) {
        BlockingQueue<BigInteger> queue = new ArrayBlockingQueue<>(40);
        PrimeProducer producer = new PrimeProducer(queue);
        try {
            System.out.printf("main Thread:%s is start, time:%s\n", Thread.currentThread().getName(), System.currentTimeMillis());
            timedRun(producer, 0, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            System.out.printf("main Thread:%s is interrupted, time:%s\n", Thread.currentThread().getName(), System.currentTimeMillis());
        }
    }
}
