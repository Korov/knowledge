package com.korov.cloud.rolemanager.provider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserEntity {
    private Long id;

    private String avtatar;

    private String name;

    private Integer age;

    private String nickname;

    private String cardno;

    private String email;

    private String phone;

    private Date createtime;

    private Date updatetime;

    private String pwd;

    private String salt;

    private String pwderrortime;

    private Integer status;

    private String statusremark;

    private String remark;
}
