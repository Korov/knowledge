package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.prototypepattern;

public class TestDemo {
    public static void main(final String[] args) {
        PrototypeObject object = new PrototypeObject();

        TestItem testItem = new TestItem();
        testItem.setValue("aaa");
        object.setItem(testItem);

        PrototypeObject object1 = object.clone();
        PrototypeObject object2 = object.deepClone();

        testItem.setValue("bbb");
        System.out.println(object.getItem().getValue());
        System.out.println(object1.getItem().getValue());
        System.out.println(object2.getItem().getValue());
    }
}
