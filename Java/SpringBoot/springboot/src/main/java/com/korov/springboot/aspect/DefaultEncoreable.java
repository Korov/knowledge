package com.korov.springboot.aspect;

import org.springframework.stereotype.Component;

@Component
public class DefaultEncoreable implements Encoreable {
    @Override
    public void performEncore() {
        System.out.println(this.getClass().getName() + ":DefaultEncoreable");
    }
}
