package org.algorithms.example.solution;

/**
 * https://leetcode-cn.com/problems/search-insert-position/
 *
 * @author zhu.lei
 * @date 2021-07-18 02:52
 */
public class Solutions35 {
    public static int searchInsert(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        int middle = (end + start) / 2;

        while (middle != start && middle != end) {
            if (nums[middle] == target) {
                return middle;
            } else {
                if (nums[middle] < target) {
                    start = middle;
                } else {
                    end = middle;
                }
                middle = (end + start) / 2;
            }
        }
        if (nums[start] == target) {
            return start;
        } else if (nums[start] < target) {
            if (nums[end] == target) {
                return end;
            } else if (nums[end] < target) {
                return end + 1;
            } else {
                return end;
            }
        } else {
            return start;
        }

    }

}
