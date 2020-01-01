package com.korov.springboot.aspect.aspectj;

import com.korov.springboot.aspect.Performance;
import org.springframework.stereotype.Component;

@Component("performanceImp")
public class PerformanceImpl implements Performance {
    //构造方法
    public PerformanceImpl() {
        System.out.println("演员进场...");
    }

    @Override
    public void perform() {
        System.out.println("表演过程中...");
    }
}
