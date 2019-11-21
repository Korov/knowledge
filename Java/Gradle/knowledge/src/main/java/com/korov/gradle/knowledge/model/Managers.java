package com.korov.gradle.knowledge.model;

import lombok.Data;

@Data
public class Managers {
    /**
    * 证券代码
    */
    private String securitiesCode;

    /**
    * 统计截止日期
    */
    private String statisticsDeadline;

    /**
    * 姓名
    */
    private String fullName;

    /**
    * 职务类别
    */
    private String jobCategory;

    /**
    * 具体职务
    */
    private String specificDuties;

    /**
    * 性别
    */
    private String gender;

    /**
    * 年龄
    */
    private String age;

    /**
    * 教育背景
    */
    private String educationalBackground;

    /**
    * 职务开始日期
    */
    private String startDateOfCurrentPosition;

    /**
    * 职务结束日期
    */
    private String endDateOfCurrentPosition;
}