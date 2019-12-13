package com.korov.gradle.knowledge;

import java.util.*;

public class MyTest {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        List<String> stringList = new ArrayList<>();
        stringList.add("a");
        stringList.add("v");
        stringList.add("s");
        stringList.stream().forEach(System.out::println);
        Iterator iterable = stringList.iterator();
        while (iterable.hasNext()) {
            iterable.next();
            iterable.remove();
            iterable.remove();
        }
        stringList.stream().forEach(System.out::println);
        Collections.unmodifiableList(stringList);
    }
}
