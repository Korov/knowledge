package org.designpatterns.example.creational.factory;


import org.designpatterns.example.creational.factory.car.Benz;
import org.designpatterns.example.creational.factory.car.Car;

class CarFactory {
    static Car getCar(final String car) throws Exception {
        if (car.equalsIgnoreCase("Benz")) {
            return new Benz();
        } else if (car.equalsIgnoreCase("BMW")) {
            return new Benz();
        } else {
            throw new Exception();
        }
    }
}
