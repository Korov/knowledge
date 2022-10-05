package org.algorithms.example.solution;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhu.lei
 * @date 2021-11-14 15:48
 */
public class Solution677 {
}

class MapSum {
    private final Map<String, Integer> innerMap = new HashMap<>(64);
    TrieNode root = new TrieNode();

    public MapSum() {
    }

    public void insert(String key, int val) {
        int delta = val - innerMap.getOrDefault(key, 0);
        innerMap.put(key, val);
        TrieNode node = root;
        for (char c : key.toCharArray()) {
            node = node.childMap.computeIfAbsent(c, mapKey -> new TrieNode());
            // 所有此前缀的和
            node.sum += delta;
        }
    }

    public int sum(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            node = node.childMap.get(c);
            if (node == null) {
                return 0;
            }
        }
        return node.sum;
    }

    class TrieNode {
        int sum = 0;
        Map<Character, TrieNode> childMap = new HashMap<>(8);
    }
}
