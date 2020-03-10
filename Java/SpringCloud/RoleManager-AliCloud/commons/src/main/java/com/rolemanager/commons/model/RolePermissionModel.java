package com.rolemanager.commons.model;

import lombok.Data;

@Data
public class RolePermissionModel {
    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 权限id
     */
    private Long permissionId;
}
