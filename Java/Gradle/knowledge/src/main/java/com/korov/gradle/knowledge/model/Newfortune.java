package com.korov.gradle.knowledge.model;

import lombok.Data;

@Data
public class Newfortune {
    private String pranUuid;

    /**
     * 姓名
     */
    private String rpiName;

    /**
     * 性别
     */
    private String scoName;

    /**
     * 学历
     */
    private String ecoName;

    /**
     * 执业机构
     */
    private String aoiName;

    /**
     * 执业岗位
     */
    private String ptiName;

    /**
     * 注册变更记录
     */
    private String countcer;

    /**
     * 诚信记录
     */
    private String countcx;

    private Integer rnum;
}
