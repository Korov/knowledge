package com.rolemanager.commons.model;

import lombok.Data;

@Data
public class UserRoleModel {
    /**
     * 角色id
     */
    private Long roleId;

    private Integer userId;
}
