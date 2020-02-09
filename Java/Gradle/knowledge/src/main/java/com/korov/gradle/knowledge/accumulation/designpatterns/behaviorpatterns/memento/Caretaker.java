package com.korov.gradle.knowledge.accumulation.designpatterns.behaviorpatterns.memento;

public class Caretaker {
    // 备忘录对象
    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}
