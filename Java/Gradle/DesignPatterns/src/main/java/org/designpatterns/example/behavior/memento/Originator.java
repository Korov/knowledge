package org.designpatterns.example.behavior.memento;

public class Originator {
    // 内部状态
    private String state = "";

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    // 创建一个备忘录
    public Memento createMemento() {
        return new Memento(state);
    }

    // 恢复一个备忘录
    public void restoreMemento(Memento memento) {
        setState(memento.getState());
    }
}
