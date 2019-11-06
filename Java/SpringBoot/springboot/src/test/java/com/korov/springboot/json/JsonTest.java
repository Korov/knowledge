package com.korov.springboot.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.korov.springboot.entity.UserEntity;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class JsonTest {
    @Test
public void test() {
    String path = "src\\test\\resources\\test\\Json.txt";

    Stream<String> lines = null;
    try {
        lines = Files.lines(Paths.get(path), StandardCharsets.UTF_8);
    } catch (IOException e) {
        e.printStackTrace();
    }
    File file = new File(path);
    String jsonString = null;
    try {
        jsonString = FileUtils.readFileToString(file, "UTF-8");
    } catch (IOException e) {
        e.printStackTrace();
    }

        /*{
            "name": "ALemon",
                "age": 24.2,
                "car": null,
                "major": [
            "敲代码",
                    "学习"
    ],
            "Nativeplace": {
            "city": "广州",
                    "country": "China"
        }
        }*/
        /*JSONObject jsonObject = JSONObject.parseObject(jsonString);
        System.out.println("获取jsonObject中的值：" + jsonObject.get("name"));
        System.out.println(jsonObject.toJSONString());
        System.out.println("获取jsonObject中的值：" + jsonObject.get("name"));
        System.out.println(jsonObject.toJSONString());
        jsonObject.put("name1", "addName");
        System.out.println(jsonObject.toJSONString());*/


    JSONArray jsonArray = JSONArray.parseArray(jsonString);
    JSONObject object1 = jsonArray.getJSONObject(0);
    object1.put("name", "addToArray"); // 会被下面的  jsonArray.getJSONObject(0).put("name", "chajjj");  覆盖掉
    jsonArray.add(object1);
    jsonArray.getJSONObject(0).put("name", "chajjj");
    System.out.println(jsonArray.getJSONObject(0).get("name") + "||" +
            jsonArray.getJSONObject(1).get("name") + "||" +
            jsonArray.getJSONObject(2).get("name"));

    List<UserEntity> list = JSONArray.parseArray(jsonString, UserEntity.class);


        /*List<UserTestEntity> list = new ArrayList<>();
        UserTestEntity entity = JSONObject.parseObject(jsonString, UserTestEntity.class);
        list.add(entity);
        entity = JSONObject.parseObject(jsonString, UserTestEntity.class);
        list.add(entity);

        String entityJsonString = JSONArray.toJSONString(list);


        System.out.println(entityJsonString);*/

    //lines.forEach(System.out::println);
    //System.out.println(jsonString);

    System.out.println("end");
}
}

