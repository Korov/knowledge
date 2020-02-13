package com.rolemanager.user.util;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class YamlUtil {
    public static Map<String, Object> getYaml(String url) {
        try {
            Yaml yaml = new Yaml();
            Reader reader = new InputStreamReader(new FileInputStream(new File(url)), StandardCharsets.UTF_8);
            return yaml.load(reader);
        } catch (FileNotFoundException e) {
            log.error("get yaml failed");
            return new HashMap<>();
        }
    }
}
