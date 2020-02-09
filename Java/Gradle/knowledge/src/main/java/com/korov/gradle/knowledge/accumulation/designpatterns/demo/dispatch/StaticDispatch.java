package com.korov.gradle.knowledge.accumulation.designpatterns.demo.dispatch;

public class StaticDispatch {
    static abstract class Human {
    }

    private static class Man extends Human {
    }

    private static class Woman extends Human {
    }

    private void sayHello(Human guy) {
        System.out.println("hello, guy!");
    }

    public void sayHello(Man guy) {
        System.out.println("hello, gentleman!");
    }

    public void sayHello(Woman guy) {
        System.out.println("hello, lady!");
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        StaticDispatch staticDispatch = new StaticDispatch();
        staticDispatch.sayHello(man);
        staticDispatch.sayHello(woman);
    }
}
