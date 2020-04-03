package org.designpatterns.example.creational.factory;


import org.designpatterns.example.creational.factory.car.Car;
import org.designpatterns.example.creational.factory.car.CarType;
import org.designpatterns.example.creational.factory.factory.CarFactory;

public class Tests {
    public static void main(String[] args) {
        try {
            Car car = CarFactory.getCar(CarType.BENZ);
            car.getCarName();
            car = CarFactory.getCar(CarType.BMW);
            car.getCarName();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
