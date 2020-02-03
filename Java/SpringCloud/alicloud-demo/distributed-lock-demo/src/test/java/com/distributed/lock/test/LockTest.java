package com.distributed.lock.test;

import com.distributed.lock.ApplicationTests;
import com.distributed.lock.mysql.LockService;
import com.distributed.lock.mysql.Tests;
import com.distributed.lock.mysql.model.TableLockMethod;
import com.distributed.lock.redis.RedisLock;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPool;

import java.util.Date;

public class LockTest extends ApplicationTests {
    @Autowired
    private RedisLock redisLock;

    static class MyThread implements Runnable {
        private final String value;

        private RedisLock redisLock;

        public MyThread(String value, RedisLock redisLock) {
            this.value = value;
            this.redisLock = redisLock;
        }

        @Override
        public void run() {
            char[] values = value.toCharArray();
            String token= redisLock.lock("demo",10000,10000);
            try {
                for (int i = 0; i < values.length; i++) {
                    System.out.print(values[i]);
                }
                System.out.println("|||");
            } finally {
                redisLock.unlock("demo",token);
            }

        }
    }

    @Test
    public void main() throws InterruptedException {
        Thread thread1 = new Thread(new MyThread("zhangsan", redisLock), "thread1");
        Thread thread2 = new Thread(new MyThread("lisi", redisLock), "thread2");
        Thread thread3 = new Thread(new MyThread("wangwu", redisLock), "thread3");
        thread1.start();
        thread2.start();
        thread3.start();
        Thread.sleep(200000);
    }
}
