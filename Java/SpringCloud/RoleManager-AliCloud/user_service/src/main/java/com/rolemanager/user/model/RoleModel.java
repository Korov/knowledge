package com.rolemanager.user.model;

import lombok.Data;

import java.util.List;

@Data
public class RoleModel {
    /**
     * 角色id
     */
    private Long id;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色代码
     */
    private String roleCode;

    private List<RoleModel> roles;
}
