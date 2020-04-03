package org.designpatterns.example.creational.prototype;

import java.io.*;

/**
 * Cloneable生命是可以克隆的
 * Serializable可以实现完全复制
 */
public class PrototypeObject implements Cloneable, Serializable {
    private static final long serialVersionUID = -332030374249484766L;
    private String value;

    PrototypeObject() {
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public PrototypeObject clone() {
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
