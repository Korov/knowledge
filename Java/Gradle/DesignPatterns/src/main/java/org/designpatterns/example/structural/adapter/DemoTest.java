package org.designpatterns.example.structural.adapter;


import org.designpatterns.example.structural.adapter.inneruser.UserInfo;
import org.designpatterns.example.structural.adapter.inneruser.UserInfoImpl;

public class DemoTest {
    public static void main(String[] args) {
        UserInfo innerUser = new UserInfoImpl();

        // 将外部用户适配成内部用户
        UserInfo outerUser = new OuterUserInfoAdapter();
    }
}
