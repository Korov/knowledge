package com.rolemanager.commons.util;

import com.rolemanager.commons.constant.Constant;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class PropertiesUtil {
    public static ResourceBundle getResource(String url) {
        try {
            Reader reader = new InputStreamReader(new FileInputStream(new File(url)), StandardCharsets.UTF_8);
            return new PropertyResourceBundle(reader);
        } catch (IOException e) {
            log.error("parase properties fail");
            // 返回空的
            return new ResourceBundle() {
                @Override
                public Enumeration<String> getKeys() {
                    return null;
                }

                @Override
                protected Object handleGetObject(String key) {
                    return null;
                }

                @Override
                public String toString() {
                    return "NONEXISTENT_BUNDLE";
                }
            };
        }
    }

    public static String getValue(String url, String key) {
        ResourceBundle resource = getResource(url);
        return getValue(resource, key);
    }

    public static String getValue(ResourceBundle resource, String key) {
        String value = null;
        try {
            value = resource.getString(key);
        } catch (MissingResourceException e) {
            log.error("can not find the key");
        }
        return value == null ? Constant.SPACE : value;
    }

    public static String getValue(String srcPath, String fileName, Locale locale, String key) {
        String url = String.format("%1$s%2$s%3$s_%4$s_%5$s.properties", srcPath, java.io.File.separator, fileName, locale.getLanguage(), locale.getCountry());
        ResourceBundle resource = getResource(url);
        return getValue(resource, key);
    }

    public static String getValue(Locale locale, String key) {
        String url = String.format("src/main/resources/i18n/%1$s_%2$s_%3$s.properties", "springboot", locale.getLanguage(), locale.getCountry());
        ResourceBundle resource = getResource(url);
        return getValue(resource, key);
    }
}
