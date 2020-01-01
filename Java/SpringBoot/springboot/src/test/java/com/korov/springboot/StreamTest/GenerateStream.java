package com.korov.springboot.StreamTest;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GenerateStream {
    public static void main(String[] args) {
        // Stream<BigInteger> integers = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE));//无限生成一个流
        /**
         * filter筛选符合filter括号中条件的数据并返回一个新的stream，
         * foreach对stream中的每个数据执行相应的操作
         */
        // integers.filter(x -> x.compareTo(BigInteger.valueOf(100L)) < 0).forEach(System.out::println);

        //forEach 和 count操作都会关闭流，如下两个操作一起执行的时候第二会报错流已经被关闭
        /*Stream<String> song = Stream.of("aaa", "bbb", "ccc");
        song.forEach(System.out::println);
        Long count = song.count();*/

        Random random = new Random();
        IntStream sss = random.ints().limit(10);
        random.ints().limit(10).forEach(System.out::println);

        //map 方法用于映射每个元素到对应的结果，以下代码片段使用 map 输出了元素对应的平方数：
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 5, 6);
        //Object[] integerMap = integerStream.map(i -> i * i).distinct().toArray();
        // map 会返回一个操作之后的流
        List<Integer> integers = integerStream.map(i -> i * i).distinct().collect(Collectors.toList());


        IntStream.of(1, 2, 3).forEach(System.out::println);
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.rangeClosed(1, 3).forEach(System.out::println);

        //将stream中的数据按照某种规则一一对应生成另外一个stream
        IntStream.range(1, 5).map((x) -> x * x).forEach(System.out::println);

        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        //inputStream.forEach(System.out::println);
//flatMap会将stream中的所欲子类stream提取到父类中
        Stream<Integer> outputStream = inputStream.flatMap((childList) -> childList.stream());
        outputStream.forEach(System.out::println);


        System.out.println("end");
    }
}
