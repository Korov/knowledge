package org.designpatterns.example.structural.proxy.dynamicproxy.proxy;

import org.designpatterns.example.structural.proxy.dynamicproxy.subject.Lamp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author korov
 * @date 2020/4/2 下午11:27
 */
public class LampHandler implements InvocationHandler {
    private Lamp target;

    public LampHandler() {
    }

    public Lamp getLampProxy(Lamp target) {
        this.target = target;
        return (Lamp) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Lamp invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Open lamp");
        Lamp object = (Lamp) method.invoke(target, args);
        System.out.println("Clos lamp");
        return object;
    }
}
