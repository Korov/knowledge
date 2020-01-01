package com.korov.gradle.knowledge.accumulation.Thread.cancell;

import java.math.BigInteger;
import java.util.List;

public class PrimeGeneratorTest {
    public static void main(String[] args) throws InterruptedException {
        PrimeGenerator generator = new PrimeGenerator();
        Thread thread = new Thread(generator);
        System.out.println("Thread state:" + thread.getState());
        thread.start();
        System.out.println("Thread state:" + thread.getState());
        List<BigInteger> list = generator.get();
        list.forEach(System.out::print);
        Thread.sleep(500);
        System.out.println("\n--------------------------");
        list = generator.get();
        list.forEach(x -> System.out.print("," + x));
        System.out.println();
        generator.cancel();
        System.out.println("Thread state:" + thread.getState());
        Thread.sleep(1000);
        System.out.println("Thread state:" + thread.getState());
    }
}
