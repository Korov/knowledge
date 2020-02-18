package com.rolemanager.user.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String dateTimeToString() {
        Date date = new Date();
        //从前端或者自己模拟一个日期格式，转为String即可
        return format.format(date);
    }

    public static Date stringToDate(String dateStr) throws ParseException {
        return format.parse(dateStr);
    }
}
