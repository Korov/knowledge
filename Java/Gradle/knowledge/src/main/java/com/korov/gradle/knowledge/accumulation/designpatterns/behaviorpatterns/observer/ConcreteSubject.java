package com.korov.gradle.knowledge.accumulation.designpatterns.behaviorpatterns.observer;

public class ConcreteSubject extends Subject {
    // 具体的业务
    public void doSomething() {
        /**
         * 业务代码
         */
        super.notifyObservers();
    }
}
