package org.designpatterns.example.structural.adapter.outeruser;

import java.util.Map;

public interface OuterUserInfo {
    // 基本信息，比如姓名，性别，手机号码
    public Map getUserBaseInfo();

    // 工作相关信息
    public Map getUserOfficeInfo();

    // 家庭信息
    public Map getUserHomeInfo();
}
