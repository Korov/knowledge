package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.prototypepattern;

import java.io.*;

/**
 * Cloneable生命是可以克隆的
 * Serializable可以实现完全复制
 */
public class PrototypeObject implements Cloneable, Serializable {
    private static final long serialVersionUID = -332030374249484766L;
    private TestItem item;

    PrototypeObject() {
    }

    TestItem getItem() {
        return item;
    }

    public void setItem(TestItem item) {
        this.item = item;
    }

    @Override
    public PrototypeObject clone() {
        PrototypeObject object = null;
        try {
            object = (PrototypeObject) super.clone();
        } catch (final CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return object;
    }

    public PrototypeObject deepClone() {
        PrototypeObject prototypeObject = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

            objectOutputStream.writeObject(this);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            prototypeObject = (PrototypeObject) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return prototypeObject;
    }
}
