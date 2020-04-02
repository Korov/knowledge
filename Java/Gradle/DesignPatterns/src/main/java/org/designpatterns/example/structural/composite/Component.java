package org.designpatterns.example.structural.composite;

public abstract class Component {
    // 个体和整体都具有的共享
    public void doSomething() {
        System.out.println("do some thing");
    }
}
