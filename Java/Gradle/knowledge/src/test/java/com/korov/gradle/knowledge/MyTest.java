package com.korov.gradle.knowledge;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTest {
    public static void main(String[] args) {
        long avalue = 0xFFFFFFFFL;
        int value = Integer.parseInt("EEEEE", 16);
        System.out.println(3 & 0xFFFFFFFFL);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(date));
    }
}
