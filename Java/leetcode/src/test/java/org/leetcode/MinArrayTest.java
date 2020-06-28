package org.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinArrayTest {
    Logger logger = LoggerFactory.getLogger(MinArrayTest.class);

    @Test
    void method1() {
        MinArray minArray = new MinArray();
        int result = minArray.method1(7, new int[]{2, 3, 1, 2, 4, 3});
        logger.info("s=7, nums=[2, 3, 1, 2, 4, 3], result={}", result);
        Assertions.assertEquals(2, result);
    }
}