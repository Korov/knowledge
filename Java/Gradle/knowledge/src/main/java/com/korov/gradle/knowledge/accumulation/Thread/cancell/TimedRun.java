package com.korov.gradle.knowledge.accumulation.Thread.cancell;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.korov.gradle.knowledge.accumulation.Thread.LaunderThrowable.launderThrowable;
import static java.util.concurrent.Executors.newScheduledThreadPool;

/**
 * TimedRun2
 * <p/>
 * Interrupting a task in a dedicated thread
 *
 * @author Brian Goetz and Tim Peierls
 */
class TimedRun {
    private static final ScheduledExecutorService cancelExec = newScheduledThreadPool(1);

    static void timedRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        class RethrowableTask implements Runnable {
            private volatile Throwable t;

            @Override
            public void run() {
                try {
                    System.out.printf("Thread:%s is start\n", Thread.currentThread().getName());
                    r.run();
                } catch (Throwable t) {
                    this.t = t;
                }
            }

            private void rethrow() {
                if (t != null) {
                    throw launderThrowable(t);
                }
            }
        }

        RethrowableTask task = new RethrowableTask();
        Thread taskThread = new Thread(task);
        System.out.printf("Thread:%s is start, time is :%s\n", Thread.currentThread().getName(), System.currentTimeMillis());
        taskThread.start();
        cancelExec.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.printf("Thread:%s begin to end task, time is :%s\n", Thread.currentThread().getName(), System.currentTimeMillis());
                taskThread.interrupt();
            }
        }, timeout, unit);
        taskThread.join(unit.toMillis(timeout));
        task.rethrow();
    }
}
