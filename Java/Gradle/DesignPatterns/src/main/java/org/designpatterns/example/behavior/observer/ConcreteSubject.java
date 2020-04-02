package org.designpatterns.example.behavior.observer;

public class ConcreteSubject extends Subject {
    // 具体的业务
    public void doSomething() {
        /**
         * 业务代码
         */
        super.notifyObservers();
    }
}
