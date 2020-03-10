package com.rolemanager.commons.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
@EqualsAndHashCode
public class UserModel {
    private Long id;

    /**
     * 用户头像
     */
    private String avtatar;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 身份证号码
     */
    private String cardno;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 盐加密
     */
    private String salt;

    /**
     * 密码错误次数
     */
    private String pwderrortime;

    /**
     * 是否可用(0可用，1不可用)
     */
    private Integer status;

    /**
     * 不可用原因
     */
    private String statusremark;

    /**
     * 备注
     */
    private String remark;

    private List<RoleModel> roles;
}
