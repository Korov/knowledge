package org.algorithms.example.solution;

public class Solutions801 {
    public static int minSwap(int[] nums1, int[] nums2) {
        int previous1 = 0;
        int previous2 = 1;
        for (int i = 1; i < nums1.length; i++) {
            if ((nums1[i] > nums1[i - 1] && nums2[i] > nums2[i - 1]) && (nums1[i] > nums2[i - 1] && nums2[i] > nums1[i - 1])) {
                previous1 = Math.min(previous1, previous2);
                previous2 = previous1 + 1;
            } else if (nums1[i] > nums1[i - 1] && nums2[i] > nums2[i - 1]) {
                previous2++;
            } else {
                int temp = previous1;
                previous1 = previous2;
                previous2 = temp + 1;
            }
        }
        return Math.min(previous1, previous2);
    }
}
