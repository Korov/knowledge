package com.korov.springboot.BaseTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

@Slf4j
public class VoidTest {
    private static final Logger logger = Logger.getLogger("com.korov.springboot.BaseTest");

    @Test
    public void test() throws Exception {
        Map<String, String> map = new TreeMap<>();
        map.put("a1", "aaa");
        map.put("a2", "aaa");
        map.put("a", "aaa");
        map.put("a3", "aaa");
        map.forEach((key, value) -> System.out.println(key + "--" + value));
    }


}
