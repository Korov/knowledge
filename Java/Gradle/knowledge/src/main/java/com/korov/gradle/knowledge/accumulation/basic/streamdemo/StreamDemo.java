package com.korov.gradle.knowledge.accumulation.basic.streamdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StreamDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        List<String> list1 = new ArrayList<>();
        list1.add("aa1");
        list1.add("bb1");
        list1.add("cc1");

        Stream<String> newList = list.stream().map(x -> x + "tt");

        Stream<String> stringStream = Stream.of(list, list1).flatMap(x -> x.stream());

        newList.forEach(x -> System.out.println(x));
        stringStream.forEach(x -> System.out.println(x));
    }
}
