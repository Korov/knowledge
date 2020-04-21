package com.korov.gradle.knowledge.accumulation.basic.lambdademo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FunctionInterfaceDemo {
    public static void main(String[] args) {
        Comparator<Integer> comparator = (x, y) -> x - y;

        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        List<Integer> list = new ArrayList<>();
        for (int i : numbers) {
            list.add(i);
        }

        // 流配合Predicate的使用方法
        Predicate<Integer> predicate1 = i -> i > 5;
        Predicate<Integer> predicate2 = i -> i < 20;
        Predicate<Integer> predicate3 = i -> i % 2 == 0;

        List result = list.stream().filter(predicate1.and(predicate2).and(predicate3)).collect(Collectors.toList());
        result.stream().forEach(System.out::println);


        // Function接口使用
        Function<Integer, Integer> function1 = i -> i + i;
        Function<Integer, Integer> function2 = i -> i * i;
        Function<Integer, Integer> function3 = function1.andThen(function2);
        Function<Integer, Integer> function4 = function1.compose(function2);
        // 4+4
        System.out.println(calculate(4, function1));
        // 4*4
        System.out.println(calculate(4, function2));
        // 先执行function1-》5+5=10  后执行function2-》10*10=100
        System.out.println(calculate(5, function3));
        // 先执行function2-》5*5=25  后执行function1-》25+25=50
        System.out.println(calculate(5, function4));
    }

    public static Integer calculate(Integer number, Function<Integer, Integer> function) {
        return function.apply(number);
    }
}
