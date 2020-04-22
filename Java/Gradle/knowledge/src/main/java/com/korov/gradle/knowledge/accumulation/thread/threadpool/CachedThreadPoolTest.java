package com.korov.gradle.knowledge.accumulation.thread.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CachedThreadPoolTest {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(200);
        for (int i = 0; i < 20; i++) {
            queue.add(String.valueOf(i));
        }
        ExecutorService threadPool = new ThreadPoolExecutor(5, 10, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        threadPool.execute(new MyRunner(queue));

        ExecutorService fixedPool = Executors.newFixedThreadPool(5);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(String.valueOf(i));
        }
        for (String value : list) {
            fixedPool.execute(new MyRunner1(value));
        }
    }

    static class MyRunner implements Runnable {
        private final BlockingQueue<String> queue;

        public MyRunner(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String value = queue.poll();
                System.out.printf("%s: %s\n", Thread.currentThread().getName(), queue);
            }
        }
    }

    static class MyRunner1 implements Runnable {
        private final String value;

        public MyRunner1(String value) {
            this.value = value;
        }

        @Override
        public void run() {
            System.out.printf("%s: %s\n", Thread.currentThread().getName(), value);
        }
    }
}
