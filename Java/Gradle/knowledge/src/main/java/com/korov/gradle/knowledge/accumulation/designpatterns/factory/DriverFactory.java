package com.korov.gradle.knowledge.accumulation.designpatterns.factory;

public class DriverFactory {
    public static Car driverCar(String car) throws Exception {
        if (car.equalsIgnoreCase("Benz")) {
            return new Benz();
        } else if (car.equalsIgnoreCase("BMW")) {
            return new Benz();
        } else {
            throw new Exception();
        }
    }
}
