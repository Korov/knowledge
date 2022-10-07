package org.algorithms.example.solution

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Solutions1Test {

    @Test
    void method2() {
        int[] nums = new int[]{2, 7, 11, 15}
        int target = 9
        Solutions1 solutions1 = new Solutions1()
        int[] result = solutions1.method2(nums, target)
        Assertions.assertArrayEquals(new int[]{0, 1}, result)
    }
}