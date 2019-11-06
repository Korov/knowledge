package com.korov.springboot.BaseTest;

public interface Test {
    default void sayHello() {
        System.out.println("Hello World!");
    }
}
