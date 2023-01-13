package com.redis.demo;

import org.junit.jupiter.api.Test;

public class Demo {
    @Test
    public void test() {
        Integer pageNo = null;
        pageNo = null == pageNo ? 1 : pageNo;
        String lineSeparator = java.security.AccessController.doPrivileged(
                new sun.security.action.GetPropertyAction("line.separator"));
    }
}
