package org.designpatterns.example.structural.proxy;

public class SubjectImpl implements Subject {
    @Override
    public void request() {
        System.out.println("I am Subject implements");
    }
}
