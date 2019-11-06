package com.korov.springboot.MoreBean;

import org.springframework.stereotype.Service;

@Service("Bean2")
public class Bean2 implements Bean {
    @Override
    public void run(String message) {
        System.out.println("This is bean2 :" + message);
    }
}

