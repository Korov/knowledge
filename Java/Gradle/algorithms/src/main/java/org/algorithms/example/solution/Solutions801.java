package org.algorithms.example.solution;

public class Solutions801 {
    public static int minSwap(int[] nums1, int[] nums2) {
        int[] min = new int[nums1.length];
        min[0] = 0;
        int temp;
        for (int i = 1; i < nums1.length; i++) {
            if (nums1[i] > nums1[i - 1] && nums2[i] > nums2[i - 1]) {
                min[i] = min[i - 1];
            } else {
                temp = nums1[i];
                nums1[i] = nums2[i];
                nums2[i] = temp;
                min[i] = min[i - 1] + 1;
            }
        }
        return min[nums1.length - 1];
    }
}
