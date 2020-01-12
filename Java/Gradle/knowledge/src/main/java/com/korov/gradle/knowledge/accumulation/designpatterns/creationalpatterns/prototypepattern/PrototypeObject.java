package com.korov.gradle.knowledge.accumulation.designpatterns.creationalpatterns.prototypepattern;

import java.io.Serializable;

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
        return this.item;
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
}
