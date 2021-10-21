package org.korov.distribution.redis_lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhu.lei
 * @date 2021-10-18 14:10
 */
@Slf4j
public class LockExample {
    private static final int QTY = 1;

    private static final long LOCK_COUNT = 100000L;

    public static void main(String[] args) {

        ExecutorService service = new ThreadPoolExecutor(5, 5, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });

        AtomicLong lockCount = new AtomicLong(0L);
        long startTime = System.currentTimeMillis();
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        config.useSingleServer().setAddress("redis://linux.korov.org:6379").setDatabase(0);
        // config.useSentinelServers().addSentinelAddress("redis://linux.korov.org:26379").setCheckSentinelsList(false).setMasterName("mymaster").setDatabase(0);
        RedissonClient redisClient = Redisson.create(config);
        RLock lock = redisClient.getLock("example_lock");

        for (int i = 0; i < QTY; ++i) {
            final int index = i;
            Callable<Void> task = new Callable<Void>() {
                @Override
                public Void call() throws Exception {

                    try {
                        String clientName = "Clinet " + index;
                        while (lockCount.get() <= LOCK_COUNT) {
                            // 尝试获取锁，10秒钟内没有获取到则放弃
                            if (!lock.tryLock(10, TimeUnit.SECONDS)) {
                                throw new IllegalStateException(System.currentTimeMillis() + " " + clientName + " could not acquire the lock");
                            }
                            try {
                                lockCount.addAndGet(1);
                                long now = System.currentTimeMillis();
                                log.info("{}:{} has the lock, count:{}, cost:{}", now, clientName, lockCount.get(), now - startTime);
                            } finally {
                                // 释放锁
                                lock.unlock();
                            }
                        }
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
