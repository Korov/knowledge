package com.korov.dubbo.user.domain;

import lombok.Data;

@Data
public class TUser {
    private Integer id;

    /**
    * 账号
    */
    private String account;

    /**
    * 地址
    */
    private String address;

    /**
    * 密码
    */
    private String password;

    /**
    * 电话号码
    */
    private String phone;

    /**
    * 积分值
    */
    private Integer point;

    private String remark;

    /**
    * 备份电话
    */
    private String telPhone;

    /**
    * 用户昵称
    */
    private String username;

    /**
    * 邮政编码
    */
    private String zipCode;

    /**
    * 账户金额
    */
    private Long balance;
}