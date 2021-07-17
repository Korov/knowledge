package org.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/climbing-stairs/
 */
public class Solution70 {
    private static final Map<Integer, Integer> cache = new HashMap<>(128);

    public int climbStairs(int n) {
        Integer result = cache.get(n);
        if (result != null) {
            return result;
        }
        if (n <= 2) {
            result = n;
        } else {
            result = climbStairs(n - 1) + climbStairs(n - 2);
        }
        cache.put(n, result);
        return result;
    }
}
