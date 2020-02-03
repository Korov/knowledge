package com.distributed.lock.mysql.model;

import java.util.Date;

/**
 * table_lock_method
 *
 * @author
 */
public class TableLockMethod {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 锁定的资源名称
     */
    private String resources;

    /**
     * 1:未分配；2：已分配
     */
    private Integer state;

    /**
     * 版本号
     */
    private Integer version;

    private String desc;

    /**
     * 保存数据时间，自动生成
     */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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