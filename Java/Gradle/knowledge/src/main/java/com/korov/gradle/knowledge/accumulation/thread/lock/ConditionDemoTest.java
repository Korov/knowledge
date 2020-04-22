package com.korov.gradle.knowledge.accumulation.thread.lock;

public class ConditionDemoTest {
    /**
     * 必须先set，否则get会一直阻塞
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ConditionDemo conditionDemo = new ConditionDemo();
        conditionDemo.setDemoVelue("test");

        String value = conditionDemo.getDemoValue();
        System.out.println(value);
    }
}
