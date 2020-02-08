package com.korov.gradle.knowledge.accumulation.designpatterns.structuralpatterns.adapterpatterns.outeruser;

import java.util.HashMap;
import java.util.Map;

/**
 * 此类是一个被适配者
 */
public class OuterUserInfoImpl implements OuterUserInfo {
    private Map<String, String> baseInfoMap = new HashMap<>();
    private Map<String, String> officeInfoMap = new HashMap<>();
    private Map<String, String> homeInfoMap = new HashMap<>();

    public void setOfficeInfoMap(Map<String, String> officeInfoMap) {
        this.officeInfoMap = officeInfoMap;
    }

    public Map<String, String> getHomeInfoMap() {
        return homeInfoMap;
    }

    public void setHomeInfoMap(Map<String, String> homeInfoMap) {
        this.homeInfoMap = homeInfoMap;
    }

    @Override
    public Map getUserBaseInfo() {
        return baseInfoMap;
    }

    @Override
    public Map getUserOfficeInfo() {
        return officeInfoMap;
    }

    @Override
    public Map getUserHomeInfo() {
        return homeInfoMap;
    }
}
