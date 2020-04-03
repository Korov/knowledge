package org.designpatterns.example.structural.proxy.staticproxy.subject;

public class CarImpl implements Car {
    @Override
    public void run() {
        System.out.println("Car is running");
    }
}
