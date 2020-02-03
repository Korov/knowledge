package com.distributed.lock.locks;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class PrintThread {
    /**
     * 使用callable实现接口
     */
    static class CallableThread implements Callable<String> {
        private final String value;

        public CallableThread(String value) {
            this.value = value;
        }

        @Override
        public String call() throws Exception {
            char[] values = value.toCharArray();
            for (int i = 0; i < values.length - 1; i++) {
                System.out.println(values[i]);
            }
            return value;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> future = new FutureTask(new CallableThread("zhangsan"));
        new Thread(future).start();
        String value = (String) future.get();
        System.out.println(value);
    }
}
