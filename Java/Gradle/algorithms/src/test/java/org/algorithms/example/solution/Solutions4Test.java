package org.algorithms.example.solution;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class Solutions4Test {

    @Test
    void findMedianSortedArrays() {
        int[] nums1 = new int[]{1, 3};
        int[] nums2 = new int[]{2};
        double result = Solutions4.findMedianSortedArrays(nums1, nums2);
        log.info("result:{}", result);

        nums1 = new int[]{1, 2};
        nums2 = new int[]{3, 4};
        result = Solutions4.findMedianSortedArrays(nums1, nums2);
        log.info("result:{}", result);
    }
}