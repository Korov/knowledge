package com.korov.gradle.knowledge.accumulation.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.time.LocalDateTime;


/**
 * 需要实现Job接口实现具体的任务内容
 *
 * @author zhu.lei
 * @version 1.0
 * @date 2020-07-09 14:02
 */
public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime.toString());
    }
}
