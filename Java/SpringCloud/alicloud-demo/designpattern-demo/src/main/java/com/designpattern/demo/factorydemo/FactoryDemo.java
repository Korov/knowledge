package com.designpattern.demo.factorydemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FactoryDemo {

    @Autowired
    private Map<String, Demo> factory = new ConcurrentHashMap<>(3);

    public Demo getDemo(String component) {
        Demo strategy = factory.get(component);
        if (strategy == null) {
            System.out.println("no strategy defined");
        }
        return strategy;
    }
}
