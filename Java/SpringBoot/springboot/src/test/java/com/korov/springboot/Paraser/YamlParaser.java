package com.korov.springboot.Paraser;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class YamlParaser {
    @Test
    public void test() {
        Yaml yaml = new Yaml();
        try {
            Reader reader = new InputStreamReader(new FileInputStream(new File("src/test/resources/paraser/yamlparaser.yml")), StandardCharsets.UTF_8);
            Map<String, Object> values = yaml.load(reader);
            Map<String, Object> childValue = (Map<String, Object>) values.get("test");
            System.out.println(childValue.get("aa"));
            System.out.println("debug");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
