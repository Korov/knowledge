package org.designpatterns.example.creational.prototype;

public class TestDemo {
    public static void main(final String[] args) {
        PrototypeObject prototype = new PrototypeObject();
        prototype.setValue("value1");

        PrototypeObject prototype1 = prototype.clone();
        prototype1.setValue("value2");

        System.out.println(prototype.getValue());
        System.out.println(prototype1.getValue());
    }
}
