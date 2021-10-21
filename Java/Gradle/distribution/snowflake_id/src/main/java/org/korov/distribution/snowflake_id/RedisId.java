package org.korov.distribution.snowflake_id;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.RedissonAtomicLong;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;

import java.io.ObjectInputFilter;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisId {
    private static final long COUNT = 100000L;

    public static void main(String[] args) {
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        config.useSingleServer().setAddress("redis://linux.korov.org:6379").setDatabase(0);
        RedissonClient redisClient = Redisson.create(config);
        redisClient.getKeys().delete("id");
        RAtomicLong lockCount = redisClient.getAtomicLong("id");
        long startTime = System.currentTimeMillis();
        long lockIndex = 0L;
        while (lockIndex <= COUNT) {
            lockIndex = lockCount.incrementAndGet();
            log.info("id:{}", lockIndex);
        }
        log.info("finish index:{}, cost:{}", lockIndex, System.currentTimeMillis() - startTime);
    }
}
