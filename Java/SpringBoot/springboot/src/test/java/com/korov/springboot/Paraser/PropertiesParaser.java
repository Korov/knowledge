package com.korov.springboot.Paraser;

import com.korov.springboot.util.PropertiesUtil;
import org.junit.Test;

import java.io.*;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropertiesParaser {
    @Test
    public void test() {
        InputStream inStream = null;
        try {
//            inStream = new BufferedInputStream(new FileInputStream(new File("F:\\MyGitHub\\GitHub\\GitHubNew\\springboot\\src\\test\\resources\\paraser\\propertiesparaser.properties")));
            inStream = new BufferedInputStream(new FileInputStream(new File("src/test/resources/paraser/propertiesparaser.properties")));
            ResourceBundle resource = new PropertyResourceBundle(inStream);
            String value = resource.getString("testkey");
            System.out.println(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1() throws Exception {
        Locale localeZh = Locale.PRC;
        Locale localeUs = Locale.US;

        String url = String.format("srcPath%1$s%2$s_%3$s_%4$s.properties", java.io.File.separator, "springboot", localeZh.getLanguage(), localeZh.getCountry());
        System.out.println(url);



        String value = PropertiesUtil.getValue(localeZh, "testkey11");
        System.out.println(value);
        value = PropertiesUtil.getValue(localeUs, "testkey");
        System.out.println(value);
        value = PropertiesUtil.getValue("src/main/resources/i18n", "springboot",localeZh,"testkey");
        System.out.println(value);
    }
}
