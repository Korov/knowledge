package org.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Solution70Test {

    @Test
    void climbStairs() {
        Solution70 solution70 = new Solution70();
        Assertions.assertEquals(3, solution70.climbStairs(3));
    }
}