package com.distributed.lock.test;

import com.distributed.lock.ApplicationTests;
import com.distributed.lock.redis.RedisLock;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPool;

public class LockTest extends ApplicationTests {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("jedisPool")
    private JedisPool jedisPool;

    //使用RedisLock
    class Outputer {
        //创建一个名为redisLock的RedisLock类型的锁
        RedisLock redisLock = new RedisLock("redisLock", redisTemplate);

        public void output(String name) {
            //上锁
            redisLock.lock();
            try {
                for (int i = 0; i < name.length(); i++) {
                    System.out.print(name.charAt(i));
                }
                System.out.println();
            } finally {
                //任何情况下都要释放锁
                redisLock.unlock();
            }
        }
    }

    @Test
    public void test() {
        final Outputer output = new Outputer();
        //线程1打印zhangsan
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("aaa");
                    output.output("zhangsan");
                }
            }
        }).start();

        //线程2打印lingsi
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    output.output("lingsi");
                }
            }
        }).start();

        //线程3打印wangwu
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    output.output("huangwu");
                }
            }
        }).start();
    }
}
