package org.algorithms.example.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.DoublePredicate;

public class ForkJoin {
    private static final Logger logger = LoggerFactory.getLogger(ForkJoin.class);

    public static void main(String[] args) {
        final int size = 10000000;
        double[] numbers = new double[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = Math.random();
        }
        Counter counter = new Counter(numbers, 0, numbers.length, x -> x > 0.5);
        ForkJoinPool pool = new ForkJoinPool();
        long startTime = System.currentTimeMillis();
        logger.info("start time:{}", startTime);
        pool.invoke(counter);
        logger.info("result:{}", counter.join());
        long endTime = System.currentTimeMillis();
        logger.info("end time:{}, cost:{}", endTime, endTime - startTime);

        int count = 0;
        startTime = System.currentTimeMillis();
        logger.info("start time:{}", startTime);
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] > 0.5) {
                count++;
            }
        }
        endTime = System.currentTimeMillis();
        logger.info("end time:{}, cost:{}", endTime, endTime - startTime);
    }


}

class Counter extends RecursiveTask<Integer> {
    public static final int THRESHOLD = 1000;
    private static final Logger logger = LoggerFactory.getLogger(Counter.class);
    private final double[] values;
    private final int from;
    private final int to;
    private final DoublePredicate filter;

    public Counter(double[] values, int from, int to, DoublePredicate filter) {
        this.values = values;
        this.from = from;
        this.to = to;
        this.filter = filter;
    }

    @Override
    protected Integer compute() {
        int count = 0;
        boolean isForked = false;
        if (to - from < THRESHOLD) {
            for (int i = from; i < to; i++) {
                if (filter.test(values[i])) {
                    count++;
                }
            }
        } else {
            isForked = true;
            int mid = (from + to) / 2;
            Counter first = new Counter(values, from, mid, filter);
            Counter second = new Counter(values, mid, to, filter);
            invokeAll(first, second);
            count = first.join() + second.join();
        }
        // logger.info("count end, is forked:{}, from:{} to:{}, count:{}", isForked, this.from, this.to, count);
        return count;
    }
}