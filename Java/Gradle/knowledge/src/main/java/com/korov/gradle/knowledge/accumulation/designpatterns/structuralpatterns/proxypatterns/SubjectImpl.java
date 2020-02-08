package com.korov.gradle.knowledge.accumulation.designpatterns.structuralpatterns.proxypatterns;

public class SubjectImpl implements Subject {
    @Override
    public void request() {
        System.out.println("I am Subject implements");
    }
}
