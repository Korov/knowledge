package com.korov.springboot.lambda;

public class HandleLambda {
    public static void main(String[] args) {
        repeat(10, () -> System.out.println("Hello World!"));
        repeat(10, i -> System.out.printf("Countdown: %s\n", 9 - i));
    }

    /**
     * 传递lambda给runnable接口
     * @param n
     * @param action
     */
    public static void repeat(int n, Runnable action) {
        for (int i = 0; i < n; i++) action.run();
    }

    /**
     * 传递给自定义接口
     * @param n
     * @param action
     */
    public static void repeat(int n, IntComsumer action) {
        for (int i = 0; i < n; i++) action.accept(i);
    }
}
