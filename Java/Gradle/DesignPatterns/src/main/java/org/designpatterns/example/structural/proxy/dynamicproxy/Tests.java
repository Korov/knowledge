package org.designpatterns.example.structural.proxy.dynamicproxy;

import org.designpatterns.example.structural.proxy.dynamicproxy.proxy.LampHandler;
import org.designpatterns.example.structural.proxy.dynamicproxy.subject.Lamp;
import org.designpatterns.example.structural.proxy.dynamicproxy.subject.LampImpl;

/**
 * @author korov
 * @date 2020/4/2 下午11:18
 */
public class Tests {
    public static void main(String[] args) {
        Lamp lamp = new LampImpl();
        LampHandler lampHandler = new LampHandler();
        Lamp proxy = (Lamp) lampHandler.getLampProxy(lamp);
        proxy.light();
    }
}
