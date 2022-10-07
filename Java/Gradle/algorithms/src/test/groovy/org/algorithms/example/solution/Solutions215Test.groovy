package org.algorithms.example.solution

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Solutions215Test {

    @Test
    void method1() {
        int[] nums = [3, 2, 1, 5, 6, 4]
        int k = 2
        Solutions215 solutions215 = new Solutions215()
        int result = solutions215.method1(nums, k)
        Assertions.assertEquals(5, result)
    }

    @Test
    void method2() {
        int[] nums = [3, 2, 1, 5, 6]
        int k = 2
        Solutions215 solutions215 = new Solutions215()
        int result = solutions215.method2(nums, k)
        Assertions.assertEquals(5, result)
    }

    @Test
    void method3() {
        int[] nums = [3, 2, 1, 5, 6]
        int k = 2
        Solutions215 solutions215 = new Solutions215()
        int result = solutions215.method3(nums, k)
        Assertions.assertEquals(5, result)
    }
}