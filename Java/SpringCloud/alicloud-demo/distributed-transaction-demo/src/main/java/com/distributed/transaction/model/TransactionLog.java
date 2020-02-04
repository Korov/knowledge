package com.distributed.transaction.model;

import java.util.Date;

public class TransactionLog {
    /**
    * 主键
    */
    private Integer id;

    /**
    * NEW：新建；PUBLISHED：已经发布到MQ中；RECEIVED：MQ传送完消息并且被接收；COMPLETE：消息处理完毕
    */
    private String state;

    /**
    * 事务的类型，例如USER_CREATE
    */
    private String transactionType;

    /**
    * 存储需要传递的信息
    */
    private String message;

    /**
    * 记录待处理的事件
    */
    private String transactionProcess;

    private String desc;

    /**
    * 数据更新时间
    */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTransactionProcess() {
        return transactionProcess;
    }

    public void setTransactionProcess(String transactionProcess) {
        this.transactionProcess = transactionProcess;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}