package org.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * <p>
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 *
 * @author korov
 * @date 2020/11/17
 */
public class Solutions3 {
    public int lengthOfLongestSubstring(String s) {
        // 记录字符上一次出现的位置
        int[] last = new int[128];
        for (int i = 0; i < 128; i++) {
            last[i] = -1;
        }
        int length = s.length();

        int res = 0;
        int start = 0;
        for (int i = 0; i < length; i++) {
            int index = s.charAt(i);
            start = Math.max(start, last[index] + 1);
            res = Math.max(res, i - start + 1);
            last[index] = i;
        }

        return res;
    }

    public int lengthOfLongestSubstring1(String s) {
        int length = s.length();
        Map<Character, Integer> map = new HashMap<>(length);
        int result = 0;
        int start = -1;
        for (int index = 0; index < length; index++) {
            char value = s.charAt(index);
            map.putIfAbsent(value, start);
            int lastIndex = map.put(value, index);
            if (lastIndex != start) {
                start = index - 1;
            }
            lastIndex = Math.max(lastIndex, start);

            result = Math.max(result, index - lastIndex);
        }
        return result;
    }
}
