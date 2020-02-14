package com.rolemanager.user.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class MenuVo {
    public String authName;
    public int id;
    public int order;
    public String path;
    public List<MenuVo> subMenus;
}
