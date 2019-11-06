package com.korov.springboot.ThreadTest.Memoizer;

public class ComputableImpl<Integer, String> implements Computable<Integer, String> {
    @Override
    public String compute(Integer arg) throws InterruptedException {
        Thread.sleep(500);
        String result = (String) "10";
        return result;
    }
}
