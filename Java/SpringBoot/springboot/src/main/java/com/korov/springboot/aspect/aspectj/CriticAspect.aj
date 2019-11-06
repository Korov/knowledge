package com.korov.springboot.aspect.aspectj;

public aspect CriticAspect {
    private CriticismEngine criticismEngine;//使用Spring 的依赖注入bean

    public CriticAspect() {}


//    pointcut applause(): execution(com.korov.springboot.aspect.aspectj.PerformanceImpl.new());
//    after(): applause() {
//        System.out.println("评论员鼓掌...");
//    }

    pointcut performance(): execution(* perform(..));
    after(): performance() {
        System.out.println("评论员：" + criticismEngine.getCriticism());
    }

    public void setCriticismEngine(CriticismEngine criticismEngine) {
        this.criticismEngine = criticismEngine;
    }
}
