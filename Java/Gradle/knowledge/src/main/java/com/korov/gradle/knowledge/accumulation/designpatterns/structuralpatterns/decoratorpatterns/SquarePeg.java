package com.korov.gradle.knowledge.accumulation.designpatterns.structuralpatterns.decoratorpatterns;

public class SquarePeg implements Work {
    @Override
    public void insert() {
        System.out.println("方柱插入");
    }
}
