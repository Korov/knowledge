package org.designpatterns.example.creational.factory.car.impl;

import org.designpatterns.example.creational.factory.car.Car;

public class Benz implements Car {

    @Override
    public void getCarName() {
        System.out.println("This is Benz");
    }
}
