package com.korov.gradle.knowledge.accumulation.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 执行方法
 *
 * @author zhu.lei
 * @version 1.0
 * @date 2020-07-09 14:16
 */
public class QuartzManager {
    public static void main(String[] args) throws SchedulerException {
        // 首先需要创建一个Scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 启动Scheduler
        scheduler.start();

        // 根据Job的实现类创建JobDetail
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("job1", "group1")
                .build();

        // 创建一个Trigger，确定什么时候触发Job
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5) //每一秒执行一次
                .repeatForever(); //永久重复，一直执行下去
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(scheduleBuilder)
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
