package org.korov.distribution.zk_lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.util.concurrent.*;

/**
 * 测试锁竞争激烈的时候的情况
 * 5个线程抢锁一直执行
 */
@Slf4j
public class LockTimesExample {
    private static final int QTY = 15;

    private static final String PATH = "/examples/locks";

    public static void main(String[] args) {

        ExecutorService service = new ThreadPoolExecutor(5, 5, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });

        for (int i = 0; i < QTY; ++i) {
            final int index = i;
            Callable<Void> task = new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    CuratorFramework client = CuratorFrameworkFactory.newClient("linux.korov.org:2181", new ExponentialBackoffRetry(10000, 3));
                    try {
                        client.start();
                        InterProcessMutex lock = new InterProcessMutex(client, PATH);
                        String clientName = "Clinet " + index;
                        while (true) {
                            // 等待锁10秒钟
                            if (!lock.acquire(10, TimeUnit.SECONDS)) {
                                throw new IllegalStateException(System.currentTimeMillis() + " " + clientName + " could not acquire the lock");
                            }
                            try {
                                log.info(System.currentTimeMillis() + " " + clientName + " has the lock");
                            } finally {
                                log.info(System.currentTimeMillis() + " " + clientName + " releasing the lock");
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
        }
    }
}
