package org.designpatterns.example.structural.proxy.staticproxy;

import org.designpatterns.example.structural.proxy.staticproxy.proxy.CarProxy;
import org.designpatterns.example.structural.proxy.staticproxy.subject.CarImpl;

/**
 * @author korov
 * @date 2020/4/2 下午11:18
 */
public class Tests {
    public static void main(String[] args) {
        CarProxy subjectProxy = new CarProxy(new CarImpl());
        subjectProxy.run();
    }
}
