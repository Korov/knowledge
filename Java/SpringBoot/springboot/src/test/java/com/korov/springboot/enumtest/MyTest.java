package com.korov.springboot.enumtest;

import org.junit.jupiter.api.Test;

import java.util.Locale;

public class MyTest {
    @Test
    public void test() {
        Locale locale = new Locale("zh", "CN");
        String value = "small".toUpperCase(locale);
        Size size = Enum.valueOf(Size.class, value);
        System.out.println("abbreviation: " + size.getAbbreviation());
    }
}
