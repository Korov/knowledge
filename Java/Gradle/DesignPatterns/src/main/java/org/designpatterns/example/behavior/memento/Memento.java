package org.designpatterns.example.behavior.memento;

public class Memento {
    // 发起人的内部状态
    private String state = "";

    // 构造函数传递参数
    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
