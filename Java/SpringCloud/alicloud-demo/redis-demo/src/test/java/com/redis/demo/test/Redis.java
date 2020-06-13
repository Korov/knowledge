package com.redis.demo.test;

import com.redis.demo.ApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

public class Redis extends ApplicationTests {

    @Autowired
    @Qualifier(value = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test() {
        redisTemplate.opsForValue().set("key1", "test333");
        System.out.println("The key value: " + redisTemplate.opsForValue().get("key1"));
    }
}
