package org.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author zhu.lei
 * @date 2021-11-14 15:54
 */
class Solution677Test {

    @Test
    void test() {
        MapSum mapSum = new MapSum();
        mapSum.insert("apple", 3);
        Assertions.assertEquals(3, mapSum.sum("ap"));
        mapSum.insert("app", 2);
        Assertions.assertEquals(5, mapSum.sum("ap"));
    }

}