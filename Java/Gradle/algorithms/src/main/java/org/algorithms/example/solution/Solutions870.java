package org.algorithms.example.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solutions870 {
    public static int[] advantageCount(int[] nums1, int[] nums2) {
        List<Integer> sortList = new ArrayList<>(nums1.length);
        Arrays.sort(nums1);
        for (int num : nums1) {
            sortList.add(num);
        }
        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            result[i] = getMid(nums2[i], sortList);
        }
        for (int i = 0; i < result.length; i++) {
            if (result[i] == -1) {
                result[i] = sortList.remove(0);
            }
        }
        return result;
    }

    private static int getMid(int num, List<Integer> nums1) {
        int left = 0;
        int right = nums1.size() - 1;
        int mid = (left + right) >> 1;
        while (right > left) {
            if (mid == left) {
                if (nums1.get(mid) > num) {
                    return nums1.remove(mid);
                } else if (nums1.get(mid) == num) {
                    if (mid == nums1.size() - 1) {
                        return -1;
                    } else {
                        while (nums1.get(mid) <= num && mid < right) {
                            mid++;
                        }
                        if (nums1.get(mid) <= num) {
                            return -1;
                        } else {
                            return nums1.remove(mid);
                        }
                    }
                } else {
                    if (nums1.get(right) <= num) {
                        return -1;
                    } else {
                        return nums1.remove(right);
                    }
                }
            } else if (nums1.get(mid) > num) {
                right = mid;
                mid = (left + right) / 2;
            } else if (nums1.get(mid) < num) {
                left = mid;
                mid = (left + right) / 2;
            } else {
                if (mid == nums1.size() - 1) {
                    return -1;
                } else {
                    while (nums1.get(mid) <= num && mid < right) {
                        mid++;
                    }
                    if (nums1.get(mid) <= num) {
                        return -1;
                    } else {
                        return nums1.remove(mid);
                    }
                }
            }
        }
        return -1;
    }
}
