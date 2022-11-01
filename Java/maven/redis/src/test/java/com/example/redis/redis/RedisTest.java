package com.example.redis.redis;

import com.example.redis.RedisApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisTest extends RedisApplicationTests {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void test() {
        redisTemplate.opsForValue().set("key", "value");
        System.out.println(redisTemplate.opsForValue().get("key"));
    }
}
