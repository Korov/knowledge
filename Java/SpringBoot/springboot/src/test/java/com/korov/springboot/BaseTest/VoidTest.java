package com.korov.springboot.BaseTest;

import com.korov.springboot.util.RegexUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
public class VoidTest {
    private static final Logger logger = Logger.getLogger("com.korov.springboot.BaseTest");

    @Test
    public void test() throws Exception {
        String value = "董事，董事长代理";

        boolean isMatch = RegexUtil.isPartMatch("首席|长", value);
        System.out.println(isMatch);

        List<String> values = new ArrayList<>();
        values.add("aaat");
        values.add("aaa2s");
        values.add("aaa3");
        values.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("debug");
    }


}
