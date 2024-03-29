package org.korov.distribution.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhu.lei
 * @date 2021-10-23 16:57
 */
@Slf4j
public class ReentrantLockTest {
    private static final int QTY = 15;

    private static final long LOCK_COUNT = 100000L;

    public static void main(String[] args) {

        ExecutorService service = new ThreadPoolExecutor(5, 5, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });

        ReentrantLock lock = new ReentrantLock();

        AtomicLong lockCount = new AtomicLong(0L);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < QTY; ++i) {
            final int index = i;
            Callable<Void> task = new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    try {
                        String clientName = "Clinet " + index;
                        while (lockCount.get() <= LOCK_COUNT) {
                            // 等待锁10秒钟
                            if (!lock.tryLock(10, TimeUnit.SECONDS)) {
                                throw new IllegalStateException(System.currentTimeMillis() + " " + clientName + " could not acquire the lock");
                            }
                            try {
                                lockCount.addAndGet(1);
                                long now = System.currentTimeMillis();
                                log.info("{} has the lock, count:{}, cost:{}", clientName, lockCount.get(), now - startTime);
                            } finally {
                                // 释放锁
                                lock.unlock();
                            }
                        }
                        long now = System.currentTimeMillis();
                        log.info("{} has the lock, count:{}, cost:{}", clientName, lockCount.get(), now - startTime);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (Exception e) {
                        e.printStackTrace();
                        // log or do something
                    }
                    return null;
                }
            };
            service.submit(task);
        }
    }
}
