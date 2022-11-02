package com.example.redis.redis;

import com.example.redis.RedisApplicationTests;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisTest extends RedisApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testString() {
        String key = "key";
        String value = "value";
        redisTemplate.opsForValue().set("key", "value");
        log.info("key:{}, type:{}, value:{}", key, redisTemplate.type(key), redisTemplate.opsForValue().get(key));
        Integer length = redisTemplate.opsForValue().append(key, "append");
        log.info("length:{}", length);
        log.info("key:{}, type:{}, value:{}", key, redisTemplate.type(key), redisTemplate.opsForValue().get(key));
        redisTemplate.opsForValue().getAndExpire(key, 1000L, TimeUnit.SECONDS);
        log.info("expire time:{}", redisTemplate.getExpire(key));
    }

    @Test
    public void testList() {
        String key = "list";
        redisTemplate.delete(key);
        String[] values = new String[3];
        values[0] = ("1");
        values[1] = ("2");
        values[2] = ("3");
        redisTemplate.opsForList().leftPush(key, "1");
        log.info("size:{}", redisTemplate.opsForList().size(key));
        log.info("key:{}, type:{}, value:{}", key, redisTemplate.type(key), redisTemplate.opsForList().range(key, 0, 1));
    }

    @Test
    void testHash() {
        String key = "hash";
        log.info("{}", redisTemplate.opsForHash().get(key, "key1"));
        redisTemplate.opsForHash().put(key, "key1", "value1");
        log.info("{}", redisTemplate.opsForHash().get(key, "key1"));

        redisTemplate.delete(key);
        redisTemplate.
    }

    @Test
    public void testKeys() {
        String key = "list";
        log.info("key:{}, type:{}, value:{}", key, redisTemplate.type(key), redisTemplate.opsForValue().get(key));
        log.info("exist:{}", redisTemplate.hasKey(key));
    }
}
