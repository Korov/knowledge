package org.designpatterns.example.structural.decorator;

public class SquarePeg implements Work {
    @Override
    public void insert() {
        System.out.println("方柱插入");
    }
}
