package com.korov.gradle.knowledge.accumulation.thread.cancell;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;

public class PrimeProducerTest {
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue queue = new ArrayBlockingQueue<BigInteger>(40);
        PrimeProducer producer = new PrimeProducer(queue);
        System.out.println("Thread state:" + producer.getState());
        producer.start();
        System.out.println("Thread state:" + producer.getState());
        Thread.sleep(500);
        producer.cancel();
        System.out.println("Thread state:" + producer.getState());
        Thread.sleep(500);
        System.out.println("Thread state:" + producer.getState());
    }
}
