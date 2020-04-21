package com.korov.gradle.knowledge.accumulation.collection.map;


import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapTest {
    public static void main(String[] args) {
        //设置为true表示按照访问顺序排序，讲最近访问的元素移动到列表末尾
        Map<String, String> map = new LinkedHashMap<String, String>(20, 0.7F, true);
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.put("key4", "value4");
        map.put("key5", "value5");
        map.put("key6", "value6");
        map.get("key6");
        map.get("key5");
        for (Map.Entry entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
