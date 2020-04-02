package org.designpatterns.example.creational.abstractfactory.factory;


import org.designpatterns.example.creational.abstractfactory.color.Color;
import org.designpatterns.example.creational.abstractfactory.shape.Circle;
import org.designpatterns.example.creational.abstractfactory.shape.Rectangle;
import org.designpatterns.example.creational.abstractfactory.shape.Shape;
import org.designpatterns.example.creational.abstractfactory.shape.Square;

public class ShapeFactory extends AbstractFactory {
    @Override
    public Shape getShape(final String shapeType) {
        Shape result = null;
        if (shapeType != null) {
            if (shapeType.equalsIgnoreCase("CIRCLE")) {
                result = new Circle();
            } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
                result = new Rectangle();
            } else if (shapeType.equalsIgnoreCase("SQUARE")) {
                result = new Square();
            }
        }
        return result;
    }

    @Override
    public Color getColor(final String color) {
        return null;
    }
}
