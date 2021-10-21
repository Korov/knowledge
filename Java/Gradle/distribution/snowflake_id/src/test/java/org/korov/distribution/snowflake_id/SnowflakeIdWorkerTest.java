package org.korov.distribution.snowflake_id;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * @author zhu.lei
 * @date 2021-10-21 10:50
 */
@Slf4j
class SnowflakeIdWorkerTest {
    private static final long COUNT = 100000L;

    @Test
    void nextId() throws Exception {
        SnowflakeIdWorker worker = new SnowflakeIdWorker();
        long index = 0;
        long startTime = System.currentTimeMillis();
        while (index <= COUNT) {
            index++;
            log.info("id:{}", worker.nextId());
        }
        log.info("finish index:{}, cost:{}", index, System.currentTimeMillis() - startTime);
    }

    @Test
    void uuid() {
        long index = 0;
        long startTime = System.currentTimeMillis();
        while (index <= COUNT) {
            index++;
            log.info("id:{}", UUID.randomUUID());
        }
        log.info("finish index:{}, cost:{}", index, System.currentTimeMillis() - startTime);
    }
}