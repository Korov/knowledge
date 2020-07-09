package com.korov.gradle.knowledge.accumulation.quartz;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 配合SpringBoot使用只需要一个注解就可以了
 * 但是需要启动类加上@EnableScheduling注解
 *
 * @author zhu.lei
 * @version 1.0
 * @date 2020-07-09 14:43
 */
@Component
public class Schedule {
    @Scheduled(cron = "* * * * * ?")
    public void printTime(){
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime.toString());
    }
}
