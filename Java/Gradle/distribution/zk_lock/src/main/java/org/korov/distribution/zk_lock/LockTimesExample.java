package org.korov.distribution.zk_lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 测试锁竞争激烈的时候的情况
 * 5个线程抢锁一直执行
 */
@Slf4j
public class LockTimesExample {
    private static final int QTY = 15;
    private static final long LOCK_COUNT = 10000L;

    private static final String PATH = "/examples/locks";

    public static void main(String[] args) throws InterruptedException {

        ExecutorService service = new ThreadPoolExecutor(5, 5, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });

        AtomicLong lockCount = new AtomicLong(0L);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < QTY; ++i) {
            final int index = i;
            Callable<Void> task = new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    // CuratorFramework client = CuratorFrameworkFactory.newClient("linux.korov.org:2181", new ExponentialBackoffRetry(10000, 3));
                    CuratorFramework client = CuratorFrameworkFactory.newClient("linux.korov.org:2181,linux.korov.org:2182,linux.korov.org:2183", new ExponentialBackoffRetry(10000, 3));
                    try {
                        client.start();
                        InterProcessMutex lock = new InterProcessMutex(client, PATH);
                        String clientName = "Clinet " + index;
                        while (lockCount.get() <= LOCK_COUNT) {
                            // 等待锁10秒钟
                            if (!lock.acquire(10, TimeUnit.SECONDS)) {
                                throw new IllegalStateException(System.currentTimeMillis() + " " + clientName + " could not acquire the lock");
                            }
                            try {
                                long count = lockCount.addAndGet(1);
                                long now = System.currentTimeMillis();
                                log.info("{}:{} has the lock, count:{}, cost:{}", now, clientName, count, now - startTime);
                            } finally {
                                // 释放锁
                                lock.release();
                            }
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (Exception e) {
                        e.printStackTrace();
                        // log or do something
                    } finally {
                        CloseableUtils.closeQuietly(client);
                    }
                    return null;
                }
            };
            service.submit(task);
            service.awaitTermination(10, TimeUnit.MINUTES);
            log.info("finish with lock count:{}, time:{}", lockCount.get(), System.currentTimeMillis() - startTime);
        }
    }
}
