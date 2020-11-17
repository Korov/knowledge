package org.leetcode;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * TODO
 *
 * @author korov
 * @date 2020/11/17
 */
class Solutions3Test {

    @ParameterizedTest
    @ValueSource(strings = {
            "abcabcbb",
            "bbbbb",
            "pwwkew",
            "",
            "au",
    })
    void lengthOfLongestSubstring(String value) {
        Solutions3 solutions3 = new Solutions3();
        int result = solutions3.lengthOfLongestSubstring(value);
        System.out.println(result);
    }
}