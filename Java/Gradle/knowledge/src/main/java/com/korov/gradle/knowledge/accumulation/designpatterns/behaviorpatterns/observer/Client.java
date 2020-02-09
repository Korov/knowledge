package com.korov.gradle.knowledge.accumulation.designpatterns.behaviorpatterns.observer;

public class Client {
    public static void main(String[] args) {
        //创建一个被观察者
        ConcreteSubject subject = new ConcreteSubject();
        //定义一个观察者
        Observer obs = new ConcreteObserver();
        // 被观察者添加观察者
        subject.addObserver(obs);
        //观察者开始活动了
        subject.doSomething();
    }
}
