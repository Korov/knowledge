package com.korov.springboot.BaseTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.logging.Logger;

@Slf4j
public class VoidTest {
    private static final Logger logger = Logger.getLogger("com.korov.springboot.BaseTest");

    @Test
    public void test() throws Exception {
        System.out.printf("i am %s", "aaa");
    }
}
