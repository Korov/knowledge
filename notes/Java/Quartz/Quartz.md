# [Quartz](https://www.w3cschool.cn/quartz_doc/quartz_doc-2put2clm.html)

Quartz是一个完全由Java编写的开源作业调度框架，为在Java应用程序中进行作业调度提供了简单却强大的机制

Quartz允许程序开发人员根据时间的间隔来调度作业

Quartz实现了作业触发器的多对多的关系，还能把多个作业与不同的触发器关联。

# 核心概念

Job：表示一个工作，要执行的具体内容。是一个接口，只有一个方法`void execute(JobExecutionContext context)`

JobDetail：表示一个具体的可执行的调度程序，Job是这个可执行调度程序所要执行的内容，另外JobDetail还包含了这个任务调度的方案和策略。

Trigger：代表一个调度参数的配置，什么时候调用

Scheduler：代表一个调度容器，一个调度容器中可以注册多个JobDetail和Trigger。当Trigger与JobDetail组合，就可以被Scheduler容器调度了。