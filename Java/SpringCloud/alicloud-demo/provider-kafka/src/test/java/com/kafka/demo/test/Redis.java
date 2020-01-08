package com.kafka.demo.test;

import com.kafka.demo.ApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

public class Redis extends ApplicationTests {

    @Autowired
    @Qualifier(value = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test() {
//        redisTemplate.opsForCluster().addSlots();
        redisTemplate.opsForSet().intersect("key", "test");
        redisTemplate.opsForValue().set("key1", "test");
        System.out.println("The key value: " + redisTemplate.opsForValue().get("key1"));
    }
}
