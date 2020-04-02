package org.designpatterns.example.creational.abstractfactory.factory;


import org.designpatterns.example.creational.abstractfactory.color.Color;
import org.designpatterns.example.creational.abstractfactory.shape.Shape;

/**
 * 为 Color 和 Shape 对象创建抽象类来获取工厂
 */
public abstract class AbstractFactory {
    public abstract Color getColor(String color);

    public abstract Shape getShape(String shape);
}
