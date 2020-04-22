package com.korov.gradle.knowledge.accumulation.thread.threadpool;

import java.util.concurrent.*;

public class CachedThreadPoolWithDaemonTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(200);
        for (int i = 0; i < 20; i++) {
            queue.add(String.valueOf(i));
        }
        ExecutorService threadPool = new ThreadPoolExecutor(5, 10, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        /**
                         * 正常情况下，线程池运行完成后不会自动停止，需要调用shutddown方法
                         * 但是将线程池中的线程设置为daemon模式，线程池中线程运行完之后线程池将自动退出
                         */
                        thread.setDaemon(true);
                        return thread;
                    }
                }, new ThreadPoolExecutor.AbortPolicy());

        Future<String> future = threadPool.submit(new MyCallable("temp1"));
        System.out.println(future.get());
    }

    static class MyCallable implements Callable<String> {
        private final String value;

        MyCallable(String value) {
            this.value = value;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(5000L);
            return Thread.currentThread().getName();
        }
    }
}
