package org.designpatterns.example.behavior.observer;

public class ConcreteObserver implements Observer {
    @Override
    public void update() {
        System.out.println("我已经接收到消息了，正在进行处理");
    }
}
