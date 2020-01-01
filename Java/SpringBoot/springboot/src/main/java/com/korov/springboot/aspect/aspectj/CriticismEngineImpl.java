package com.korov.springboot.aspect.aspectj;

public class CriticismEngineImpl implements CriticismEngine {
    private String[] criticismPool;//评论池，即评论员的评论集合

    public CriticismEngineImpl(){}

    public void setCriticismPool(String[] criticismPool){
        this.criticismPool = criticismPool;
    }

    /**
     * 随机从评论池中选取一条评论
     */
    @Override
    public String getCriticism() {
        int i = (int) (Math.random() * criticismPool.length);
        return criticismPool[i];
    }
}
