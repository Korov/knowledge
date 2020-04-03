package org.designpatterns.example.creational.factory.factory;


import org.designpatterns.example.creational.factory.car.Car;
import org.designpatterns.example.creational.factory.car.CarType;
import org.designpatterns.example.creational.factory.car.impl.Benz;
import org.designpatterns.example.creational.factory.car.impl.Bmw;

public class CarFactory {
    public static Car getCar(CarType carType) throws Exception {
        Car car = null;
        switch (carType) {
            case BENZ:
                car = new Benz();
                break;
            case BMW:
                car = new Bmw();
                break;
            default:
                throw new Exception("There is no this type car!");
        }
        return car;
    }
}
