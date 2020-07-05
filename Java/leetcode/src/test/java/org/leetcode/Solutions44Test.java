package org.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * TODO
 *
 * @author korov
 * @date 2020/7/5
 */
class Solutions44Test {

    @Test
    void method1() {
        Solutions44 solutions44 = new Solutions44();
        String s = "abc e";
        String p = "*";
        boolean isMatch = solutions44.method1(s, p);
        Assertions.assertEquals(true, isMatch);
    }

    @Test
    void method2() {
        Solutions44 solutions44 = new Solutions44();
        String s = "abc e";
        String p = "*";
        boolean isMatch = solutions44.method2(s, p);
        Assertions.assertEquals(true, isMatch);
    }
}