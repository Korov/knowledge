package com.korov.gradle.knowledge.accumulation.designpatterns.structuralpatterns.adapterpatterns;

import com.korov.gradle.knowledge.accumulation.designpatterns.structuralpatterns.adapterpatterns.inneruser.UserInfo;
import com.korov.gradle.knowledge.accumulation.designpatterns.structuralpatterns.adapterpatterns.outeruser.OuterUserInfoImpl;

import java.util.Map;

/**
 * 适配器
 */
public class OuterUserInfoAdapter extends OuterUserInfoImpl implements UserInfo {
    private Map<String, String> baseInfoMap = super.getUserBaseInfo();
    private Map<String, String> officeInfoMap = super.getUserOfficeInfo();
    private Map<String, String> homeInfoMap = super.getHomeInfoMap();

    @Override
    public String getUserName() {
        return baseInfoMap.get("USER_NAME");
    }

    @Override
    public String getHomeAddress() {
        return homeInfoMap.get("HOME_ADDRESS");
    }

    @Override
    public String getMobileNumber() {
        return baseInfoMap.get("MOBILE_NUMBER");
    }

    @Override
    public String getOfficeNumber() {
        return officeInfoMap.get("OFFICE_NUMBER");
    }

    @Override
    public String getJobPosition() {
        return officeInfoMap.get("JOB_POSITION");
    }

    @Override
    public String getHomeNumber() {
        return homeInfoMap.get("HOME_NUMBER");
    }
}
