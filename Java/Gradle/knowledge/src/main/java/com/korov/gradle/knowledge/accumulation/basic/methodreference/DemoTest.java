package com.korov.gradle.knowledge.accumulation.basic.methodreference;

public class DemoTest {
    public static void main(String[] args) {
        Demo demo = x -> System.out.println(x + x + x);
        String value = "test";
        test(value, demo);

        MethodDemo demo1 = new MethodDemo();
        test(value, demo1::method);
    }

    public static void test(String value, Demo demo) {
        demo.method1(value);
    }
}
