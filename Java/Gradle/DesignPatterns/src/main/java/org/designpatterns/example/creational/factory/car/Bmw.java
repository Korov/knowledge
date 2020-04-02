package org.designpatterns.example.creational.factory.car;

public class Bmw implements Car {
    @Override
    public void getCarName() {
        System.out.println("Driving BMW");
    }
}
