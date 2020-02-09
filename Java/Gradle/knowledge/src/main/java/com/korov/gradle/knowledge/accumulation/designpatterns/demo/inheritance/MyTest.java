package com.korov.gradle.knowledge.accumulation.designpatterns.demo.inheritance;

public class MyTest {
    public static void main(String[] args) {
        Father[] fathers = new Father[2];
        Father father = new Father();
        Son son = new Son();
        fathers[0] = father;
        fathers[1] = son;
        son.setAge("12");
        Son son1 = (Son) fathers[1];
        son1.getAge();
        Son son2 = new Son();
        boolean is = fathers[0] instanceof Son;
        System.out.println(son2.getAge());
    }

    static void test() {
        System.out.println("protect");
    }
}
