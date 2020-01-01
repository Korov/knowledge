package com.korov.springboot.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Audience {
    @Before("execution(* com.korov.springboot.aspect.Performance.perform(..))")
    public void silenceCellPhones() {
        System.out.println(this.getClass().getName() + ":Silencing cell phones");
    }

    @Before("execution(* com.korov.springboot.aspect.Performance.perform(..))")
    public void takeSeats() {
        System.out.println(this.getClass().getName() + ":Taking seats");
    }

    @AfterReturning("execution(* com.korov.springboot.aspect.Performance.perform(..))")
    public void applause() {
        System.out.println(this.getClass().getName() + ":CLAP...");
    }

    @AfterThrowing("execution(* com.korov.springboot.aspect.Performance.perform(..))")
    public void demandRefund() {
        System.out.println(this.getClass().getName() + ":Demanding a refund");
    }
}
