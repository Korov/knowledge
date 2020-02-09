package com.korov.gradle.knowledge.accumulation.designpatterns.behaviorpatterns.observer;

import java.util.Vector;

public abstract class Subject {
    // 定义一个观察者数组
    private Vector<Observer> observers = new Vector<Observer>();

    // 增加一个观察者
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // 删除一个观察者
    public void delObserver(Observer observer) {
        observers.remove(observer);
    }

    // 通知所有观察者
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
