package com.rolemanager.commons.model.users;

import lombok.Data;

@Data
public class PermissionModel {
    /**
     * 权限id
     */
    private Long id;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限代码
     */
    private String permissionCode;
}
