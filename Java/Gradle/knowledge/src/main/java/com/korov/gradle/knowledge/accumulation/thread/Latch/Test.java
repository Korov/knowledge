package com.korov.gradle.knowledge.accumulation.thread.Latch;

public class Test {
    public static void main(String[] args) {
        LatchThread task = new LatchThread();
        LatchTask harness = new LatchTask();
        try {
            LatchTask.timeTasks(10, task);
        } catch (InterruptedException e) {
            System.out.printf("run fail!");
        }
    }
}
