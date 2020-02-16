package com.rolemanager.user.controller;

import com.rolemanager.user.constant.Constant;
import com.rolemanager.user.vo.MenuVo;
import com.rolemanager.user.vo.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Menus {

    @GetMapping(value = "/menus")
    public ResultVo getMenus() {
        List<MenuVo> menus = new ArrayList<>();
        List<MenuVo> subMenus = null;

        MenuVo userMenu = new MenuVo();
        userMenu.setAuthName("用户管理");
        userMenu.setId(125);
        userMenu.setOrder(1);
        userMenu.setPath("users");
        menus.add(userMenu);

        subMenus = new ArrayList<>();
        MenuVo userList = new MenuVo();
        userList.setAuthName("用户列表");
        userList.setId(136);
        userList.setOrder(1);
        userList.setPath("userList");
        subMenus.add(userList);
        userMenu.setSubMenus(subMenus);

        MenuVo rightsMenu = new MenuVo();
        rightsMenu.setAuthName("权限管理");
        rightsMenu.setId(103);
        rightsMenu.setOrder(2);
        rightsMenu.setPath("rights");
        menus.add(rightsMenu);

        subMenus = new ArrayList<>();
        MenuVo roleList = new MenuVo();
        roleList.setAuthName("角色列表");
        roleList.setId(137);
        roleList.setOrder(1);
        roleList.setPath("roleList");
        subMenus.add(roleList);
        MenuVo rightList = new MenuVo();
        rightList.setAuthName("权限列表");
        rightList.setId(138);
        rightList.setOrder(2);
        rightList.setPath("rightList");
        subMenus.add(rightList);
        rightsMenu.setSubMenus(subMenus);

        MenuVo goodsMenu = new MenuVo();
        goodsMenu.setAuthName("商品管理");
        goodsMenu.setId(101);
        goodsMenu.setOrder(3);
        goodsMenu.setPath("goods");
        menus.add(goodsMenu);

        subMenus = new ArrayList<>();
        MenuVo goodsList = new MenuVo();
        goodsList.setAuthName("商品列表");
        goodsList.setId(139);
        goodsList.setOrder(1);
        goodsList.setPath("goodsList");
        subMenus.add(goodsList);
        MenuVo categoryList = new MenuVo();
        categoryList.setAuthName("分类参数");
        categoryList.setId(140);
        categoryList.setOrder(2);
        categoryList.setPath("categoryVar");
        subMenus.add(categoryList);
        categoryList = new MenuVo();
        categoryList.setAuthName("商品分类");
        categoryList.setId(141);
        categoryList.setOrder(3);
        categoryList.setPath("goodsCategory");
        subMenus.add(categoryList);
        goodsMenu.setSubMenus(subMenus);

        MenuVo orderMenu = new MenuVo();
        orderMenu.setAuthName("订单管理");
        orderMenu.setId(102);
        orderMenu.setOrder(4);
        orderMenu.setPath("orders");
        menus.add(orderMenu);

        subMenus = new ArrayList<>();
        MenuVo orderList = new MenuVo();
        orderList.setAuthName("订单列表");
        orderList.setId(142);
        orderList.setOrder(1);
        orderList.setPath("orderList");
        subMenus.add(orderList);
        orderMenu.setSubMenus(subMenus);

        MenuVo reportMenu = new MenuVo();
        reportMenu.setAuthName("数据统计");
        reportMenu.setId(145);
        reportMenu.setOrder(5);
        reportMenu.setPath("reports");
        menus.add(reportMenu);

        subMenus = new ArrayList<>();
        MenuVo reportList = new MenuVo();
        reportList.setAuthName("数据统计");
        reportList.setId(142);
        reportList.setOrder(1);
        reportList.setPath("dataCal");
        subMenus.add(reportList);
        reportMenu.setSubMenus(subMenus);

        return new ResultVo(Constant.OPERATION_SUCCESS, Constant.DESCRIPTION_SUCCESS, menus);
    }
}
