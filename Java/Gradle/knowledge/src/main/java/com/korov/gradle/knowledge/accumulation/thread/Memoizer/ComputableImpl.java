package com.korov.gradle.knowledge.accumulation.thread.Memoizer;

public class ComputableImpl<Integer, String> implements Computable<Integer, String> {
    @Override
    public String compute(Integer arg) throws InterruptedException {
        Thread.sleep(500);
        String result = (String) "10";
        return result;
    }
}
