package org.korov.flink.common.source;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author korov
 */
@Slf4j
public class RedisTest {
    @Test
    public void test() {
        RedisClient redisClient = RedisClient.create("redis://@192.168.50.100:6379/0");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();

        String key = "hello";
        String value = "world";
        syncCommands.set(key, value);

        String result = syncCommands.get(key);
        log.info("key:{}, result:{}", key, result);

        connection.close();
        redisClient.shutdown();
    }
}
