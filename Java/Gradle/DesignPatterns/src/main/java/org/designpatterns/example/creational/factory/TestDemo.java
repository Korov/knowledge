package org.designpatterns.example.creational.factory;


import org.designpatterns.example.creational.factory.car.Car;

public class TestDemo {
    public static void main(final String[] args) {
        try {
            final Car car = CarFactory.getCar("benz");
            car.getCarName();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
