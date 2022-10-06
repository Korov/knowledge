package org.algorithms.example.solution;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Solutions70Test {

    @Test
    void climbStairs() {
        Solutions70 solution70 = new Solutions70();
        Assertions.assertEquals(3, solution70.climbStairs(3));
    }
}