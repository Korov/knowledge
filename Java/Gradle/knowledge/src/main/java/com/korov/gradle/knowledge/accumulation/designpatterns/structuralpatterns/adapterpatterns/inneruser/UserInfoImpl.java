package com.korov.gradle.knowledge.accumulation.designpatterns.structuralpatterns.adapterpatterns.inneruser;

public class UserInfoImpl implements UserInfo {
    private String userName;

    private String homeAddress;

    private String mobileNumber;

    private String officeNumber;

    private String jobPosition;

    private String homeNumber;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getHomeAddress() {
        return homeAddress;
    }

    @Override
    public String getMobileNumber() {
        return mobileNumber;
    }

    @Override
    public String getOfficeNumber() {
        return officeNumber;
    }

    @Override
    public String getJobPosition() {
        return jobPosition;
    }

    @Override
    public String getHomeNumber() {
        return homeNumber;
    }
}
