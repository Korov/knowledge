package com.korov.springboot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 * @author korov
 */
public final class YamlUtil {

    private YamlUtil() {
    }

    /**
     * 日志文件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(YamlUtil.class);

    /**
     * 通过文件路径获取文件
     *
     * @param filePath
     * @param <T>
     * @return 返回解析结果
     * @throws FileNotFoundException
     */
    public static <T> T getYaml(final String filePath) throws FileNotFoundException {
        Reader reader = getReader(filePath);
        Yaml yaml = new Yaml();
        return (T) yaml.load(reader);
    }

    private static Reader getReader(final String filePath) throws FileNotFoundException {
        if (filePath == null) {
            throw new NullPointerException("please enter file path!");
        }
        File yamlFile = new File(filePath);
        if (!yamlFile.exists()) {
            LOGGER.error("please check the file path {}", filePath);
            throw new FileNotFoundException(String.format("Can not find the yaml file in path:%s", filePath));
        }
        Reader reader = new InputStreamReader(new FileInputStream(yamlFile), StandardCharsets.UTF_8);
        return reader;
    }
}

