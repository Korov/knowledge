package com.korov.springboot.MoreBean;

import org.springframework.stereotype.Service;

@Service("Bean1")
public class Bean1 implements Bean {
    @Override
    public void run(String message) {
        System.out.println("This is bean1 :" + message);
    }
}
