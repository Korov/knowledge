package org.algorithms.example.solution;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author zhu.lei
 * @date 2021-07-18 03:10
 */
class Solutions35Test {

    @Test
    void searchInsert() {
        int[] nums = new int[]{1, 3, 5, 6};
        Solutions35 solution35 = new Solutions35();
        Assertions.assertEquals(4, Solutions35.searchInsert(nums, 2));
    }
}