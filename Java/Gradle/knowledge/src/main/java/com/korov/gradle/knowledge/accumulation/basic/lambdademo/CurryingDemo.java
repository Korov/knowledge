package com.korov.gradle.knowledge.accumulation.basic.lambdademo;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 柯里化
 */
public class CurryingDemo {
    public static void main(String[] args) {
        BiFunction<Integer, Integer, Integer> function = Integer::sum;

        Function<Integer, Integer> newFunction = currying(function, 1234);
        System.out.println(newFunction.apply(3));

        // 上面的内容相当于函数式中调用另外一个函数式
        Function<Integer, Integer> myFunction = x -> function.apply(x, 1234);
        System.out.println(myFunction.apply(3));
    }

    public static Function<Integer, Integer> currying(BiFunction<Integer, Integer, Integer> function, int value) {
        return x -> function.apply(x, value);
    }
}
