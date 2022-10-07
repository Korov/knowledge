package org.algorithms.example.solution

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

/**
 * TODO
 *
 * @author korov
 * @date 2020/11/17
 */
class Solutions3Test {

    @ParameterizedTest
    @ValueSource(strings = [
            "abcabcbb",
            "bbbbb",
            "",
            "au",
            " ",
            "pwwkew",
            "aab",
            "abba",
    ])
    void lengthOfLongestSubstring(String value) {
        Solutions3 solutions3 = new Solutions3()
        int result = solutions3.lengthOfLongestSubstring(value)
        System.out.println(result)
    }

    @Test
    void test() {
        List<String> values = new ArrayList<>()
        values.add("a")
        values.add("a1")
        values.add("a2")
        Map<String, Integer> map = new HashMap<>()
        for (int i = 0; i < values.size(); i++) {
            map.put(values.get(i), -1)
            int vlaue = map.put(values.get(i), i)
            System.out.println("debug")
        }
    }
}