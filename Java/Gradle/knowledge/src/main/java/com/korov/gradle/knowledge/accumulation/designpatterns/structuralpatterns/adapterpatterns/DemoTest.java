package com.korov.gradle.knowledge.accumulation.designpatterns.structuralpatterns.adapterpatterns;

import com.korov.gradle.knowledge.accumulation.designpatterns.structuralpatterns.adapterpatterns.inneruser.UserInfo;
import com.korov.gradle.knowledge.accumulation.designpatterns.structuralpatterns.adapterpatterns.inneruser.UserInfoImpl;

public class DemoTest {
    public static void main(String[] args) {
        UserInfo innerUser = new UserInfoImpl();

        // 将外部用户适配成内部用户
        UserInfo outerUser = new OuterUserInfoAdapter();
    }
}
