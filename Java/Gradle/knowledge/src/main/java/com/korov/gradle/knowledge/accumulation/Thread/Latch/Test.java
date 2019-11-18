package com.korov.gradle.knowledge.accumulation.Thread.Latch;

public class Test {
    public static void main(String[] args) {
        TestTask task = new TestTask();
        TestHarness harness = new TestHarness();
        try {
            TestHarness.timeTasks(10, task);
        } catch (InterruptedException e) {
            System.out.printf("run fail!");
        }
    }
}
