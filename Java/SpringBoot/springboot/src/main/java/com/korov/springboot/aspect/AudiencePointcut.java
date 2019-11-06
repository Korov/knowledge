package com.korov.springboot.aspect;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AudiencePointcut {
    @Pointcut("execution(* com.korov.springboot.aspect.Performance.perform(..))")
    public void performance() {
    }

    @Before("performance()")
    public void silenceCellPhones() {
        System.out.println(this.getClass().getName() + ":Silencing cell phones");
    }

    @Before("performance()")
    public void takeSeats() {
        System.out.println(this.getClass().getName() + ":Taking seats");
    }

    @AfterReturning("performance()")
    public void applause() {
        System.out.println(this.getClass().getName() + ":CLAP...");
    }

    @AfterThrowing("performance()")
    public void demandRefund() {
        System.out.println(this.getClass().getName() + ":Demanding a refund");
    }
}
