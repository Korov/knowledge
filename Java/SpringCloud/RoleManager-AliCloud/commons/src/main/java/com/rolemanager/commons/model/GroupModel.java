package com.rolemanager.commons.model;

import lombok.Data;

@Data
public class GroupModel {
    /**
     * 组id
     */
    private Long id;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 组名称
     */
    private String groupName;

    /**
     * 组代码
     */
    private String groupCode;
}
