package com.korov.springboot.ThreadTest.FutureTask;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        PreLoader preLoader = new PreLoader();
        preLoader.start();
        ProductInfo productInfo = preLoader.get();
        System.out.printf("Debug!\n");
    }
}
