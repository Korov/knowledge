package com.korov.gradle.knowledge.model;

import lombok.Data;

import java.util.List;

@Data
public class Demo {
    private String code;

    private String date;

    private String name;

    private String zhiwuleibie;

    private String jutizhiwu;

    private String sex;

    private String age;

    private String edu;

    private String startdate;

    private String enddate;

    private String isdelete;

    private List<String> zhiwus;
}
