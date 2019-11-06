package com.korov.springboot.ThreadTest.Latch;

public class Test {
    public static void main(String[] args) {
        TestTask task = new TestTask();
        TestHarness harness = new TestHarness();
        try {
            harness.timeTasks(10, task);
        } catch (InterruptedException e) {
            System.out.printf("run fail!");
        }
    }
}
