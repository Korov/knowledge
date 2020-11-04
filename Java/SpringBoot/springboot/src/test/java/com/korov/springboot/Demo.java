package com.korov.springboot;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhu.lei
 * @version 1.0
 * @date 2020-10-28 37:29
 */
public class Demo {
    @Test
    public void test() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1001; i++) {
            list.add(String.valueOf(i));
        }

        int pageSize = 10;
        int totalCount = list.size();
        for (int i = 0; i < totalCount; i += pageSize) {
            int fromIndex = i;
            int toIndex = Math.min(i + pageSize, totalCount);
            List<String> temp = list.subList(fromIndex, toIndex);
            System.out.println(JSONObject.toJSONString(temp));
        }
        System.out.println("debug");
    }
}
