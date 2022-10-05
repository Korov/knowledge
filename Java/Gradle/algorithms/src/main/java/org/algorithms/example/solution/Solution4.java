package org.algorithms.example.solution;

public class Solution4 {
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length = nums1.length + nums2.length;
        int mid = length / 2;
        int[] midValues = new int[2];
        int index1 = 0;
        int index2 = 0;
        for (int index = 0; index < length; index++) {
            if (index1 < nums1.length && index2 < nums2.length) {
                if (nums1[index1] <= nums2[index2]) {
                    if (index == mid - 1 || index == mid) {
                        midValues[index - mid + 1] = nums1[index1];
                    }
                    index1++;
                } else {
                    if (index == mid - 1 || index == mid) {
                        midValues[index - mid + 1] = nums2[index2];
                    }
                    index2++;
                }
            } else {
                if (index1 >= nums1.length) {
                    if (index == mid - 1 || index == mid) {
                        midValues[index - mid + 1] = nums2[index2];
                    }
                    index2++;
                } else {
                    if (index == mid - 1 || index == mid) {
                        midValues[index - mid + 1] = nums1[index1];
                    }
                    index1++;
                }
            }
        }
        if (length % 2 == 0) {
            return (midValues[0] * 1.0D + midValues[1]) / 2;
        } else {
            return midValues[1];
        }
    }
}
