package com.korov.gradle.knowledge.accumulation.thread.mythreadtest;


import java.util.concurrent.*;

public class TimeoutFuture implements Callable {


    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        return "Value";
    }

    private static void cnacel() {
        Thread.currentThread().interrupt();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        TimeoutFuture timeoutThread = new TimeoutFuture();
        Future<String> future = executorService.submit(timeoutThread);

        try {
            System.out.println(future.get(100, TimeUnit.MILLISECONDS));
        } catch (TimeoutException e) {
            System.out.println("time out");
            future.cancel(true);
            executorService.shutdown();
        }
    }
}
