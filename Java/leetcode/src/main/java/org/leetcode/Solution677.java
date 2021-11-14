package org.leetcode;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhu.lei
 * @date 2021-11-14 15:48
 */
public class Solution677 {
}

class MapSum {
    private final LinkedHashMap<String, Integer> innerMap = new LinkedHashMap<>(64);

    public MapSum() {
    }

    public void insert(String key, int val) {
        innerMap.put(key, val);
    }

    public int sum(String prefix) {
        int sum = 0;
        for (Map.Entry<String, Integer> entry : innerMap.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                sum += entry.getValue();
            }
        }
        return sum;
    }
}
