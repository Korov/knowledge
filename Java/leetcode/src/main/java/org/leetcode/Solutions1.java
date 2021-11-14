package org.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两边
 * 示例：
 * 给定：nums = [2, 7, 11, 15], target = 9，因为 nums[0] + nums[1] = 2 + 7 = 9，所以返回 [0, 1]
 *
 * @author korov
 */
public class Solutions1 {
    public int[] method2(int[] nums, int target) {
        Map<Integer, Integer> sumKey = new HashMap<>(16);
        for (int i = 0; i < nums.length; i++) {
            int element = target - nums[i];
            if (sumKey.containsKey(element) && (i != sumKey.get(element))) {
                return new int[]{sumKey.get(element), i};
            }
            sumKey.put(nums[i], i);
        }
        return null;
    }
}
