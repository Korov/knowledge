package com.korov.springboot.StreamIO;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErrorMsg {
    String resultNo;
    String errorNo;
    String errorMsg;

    public ErrorMsg(String errorNo,String errorMsg){
        setErrorNo(errorNo);
        this.errorMsg=errorMsg;
    }

    public String getErrorNo() {
        return errorNo;
    }

    public String getResultNo() {
        return resultNo;
    }

    public void setErrorNo(String errorNo) {
        this.errorNo = errorNo;
        this.resultNo = finds("#\\d", errorNo).get(0);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    private List<String> finds(String regex, String content) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        List<String> matchers = new ArrayList<>(5);
        while (matcher.find()) {
            matchers.add(content.substring(matcher.start(), matcher.end()));
        }
        return matchers;
    }
}
