package org.designpatterns.example.structural.proxy.dynamicproxy.subject;

public class LampImpl implements Lamp {
    @Override
    public void light() {
        System.out.println("Lamp is lighting");
    }
}
