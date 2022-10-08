package org.algorithms.example.solution;

import java.util.Arrays;

public class Solutions870 {
    public static int[] advantageCount(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        int[] result = new int[nums1.length];
        int previousIndex = -1;
        int index;
        for (int i = 0; i < nums1.length; i++) {
           int nums = nums2[i];
            index = nums1.length / 2;
            while (Math.abs(previousIndex - index) > 1) {
                if (nums1[index-1] < nums) {
                    previousIndex = index;
                    index = (nums1.length + index) / 2;
                } else if (nums1[index - 1] > nums) {
                    previousIndex = index;
                    index = index / 2;
                } else {
                    result[i] = nums1[index];
                }
            }
            if (previousIndex - index > 0) {
                result[i] = nums1[previousIndex];
            } else {
                result[i] = nums1[index];
            }
        }
        return result;
    }
}
