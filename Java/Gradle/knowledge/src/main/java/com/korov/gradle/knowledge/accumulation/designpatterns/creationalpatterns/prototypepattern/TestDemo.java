package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.prototypepattern;

public class TestDemo {
    public static void main(final String[] args) {
        PrototypeObject object = new PrototypeObject();
        PrototypeObject object1 = object.clone();
        TestItem testItem = new TestItem();
        testItem.setValue("aaa");
        System.out.println(object.getItem().getValue() + "  :  " + object1.getItem().getValue());
    }
}
