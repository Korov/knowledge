package com.korov.gradle.knowledge.accumulation.designpatterns.factory;

public class TestDemo {
    public static void main(String[] args) {
        try {
            Car car = DriverFactory.driverCar("benz");
            car.drive();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
