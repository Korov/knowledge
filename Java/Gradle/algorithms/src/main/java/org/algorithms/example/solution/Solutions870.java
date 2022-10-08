package org.algorithms.example.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Solutions870 {
    public static int[] advantageCount(int[] nums1, int[] nums2) {
        List<Integer> sortList = new ArrayList<>(nums1.length);
        Arrays.sort(nums1);
        for (int num : nums1) {
            sortList.add(num);
        }
        int[] result = new int[nums1.length];
        List<Integer> indexSet = new ArrayList<>(nums1.length);
        for (int i = 0; i < nums1.length; i++) {
            getMid(result, nums2, i, sortList, indexSet);
        }
        for (Integer index : indexSet) {
            result[index] = sortList.remove(0);
        }
        return result;
    }

    // exceed time
    private static void getMid1(int[] result, int[] nums2, int index, List<Integer> sortList, List<Integer> indexSet) {
        Iterator<Integer> value = sortList.iterator();
        while (value.hasNext()) {
            int num = value.next();
            if (num > nums2[index]) {
                value.remove();
                result[index] = num;
                return;
            }
        }
        indexSet.add(index);
    }


    private static int getMid(int[] result, int[] nums2, int index, List<Integer> sortList, List<Integer> indexSet) {
        int left = 0;
        int right = sortList.size() - 1;
        int mid = (left + right) >> 1;
        while (right > left) {
            if (mid == left) {
                if (sortList.get(mid) > nums2[index]) {
                    return result[index] = sortList.remove(mid);
                } else if (sortList.get(mid) == nums2[index]) {
                    if (mid == sortList.size() - 1) {
                        indexSet.add(index);
                        return -1;
                    } else {
                        while (sortList.get(mid) <= nums2[index] && mid < right) {
                            mid++;
                        }
                        if (sortList.get(mid) <= nums2[index]) {
                            indexSet.add(index);
                            return -1;
                        } else {
                            return result[index] = sortList.remove(mid);
                        }
                    }
                } else {
                    if (sortList.get(right) <= nums2[index]) {
                        indexSet.add(index);
                        return -1;
                    } else {
                        return result[index] = sortList.remove(right);
                    }
                }
            } else if (sortList.get(mid) > nums2[index]) {
                right = mid;
                mid = (left + right) / 2;
            } else if (sortList.get(mid) < nums2[index]) {
                left = mid;
                mid = (left + right) / 2;
            } else {
                if (mid == sortList.size() - 1) {
                    indexSet.add(index);
                    return -1;
                } else {
                    while (sortList.get(mid) <= nums2[index] && mid < right) {
                        mid++;
                    }
                    if (sortList.get(mid) <= nums2[index]) {
                        indexSet.add(index);
                        return -1;
                    } else {
                        return result[index] = sortList.remove(mid);
                    }
                }
            }
        }
        indexSet.add(index);
        return -1;
    }
}
