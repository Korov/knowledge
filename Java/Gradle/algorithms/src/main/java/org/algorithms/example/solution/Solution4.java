package org.algorithms.example.solution;

public class Solution4 {
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length = nums1.length + nums2.length;
        int mid;
        int index1 = 0;
        int index2 = 0;
        if (length % 2 == 0) {
            mid = length / 2;
            for (int index = 0; index < length; index++) {
                if (index1 >= nums1.length - 1) {
                    index2++;
                    if (index == mid) {
                        return (nums2[index2] + nums2[index2 + 1]) / 2;
                    }
                    continue;
                }
                if (index2 >= nums2.length - 1) {
                    index1++;
                    if (index == mid) {
                        return (nums1[index1] + nums1[index1 + 1]) / 2;
                    }
                    continue;
                }
                if (nums1[index1] <= nums2[index2]) {
                    in
                }
            }
        } else {
            mid = length / 2;
        }
        return 0D;
    }
}
