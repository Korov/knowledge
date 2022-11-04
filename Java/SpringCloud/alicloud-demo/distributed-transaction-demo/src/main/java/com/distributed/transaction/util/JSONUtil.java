package com.distributed.transaction.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * json与类互相转换工具
 *
 * @author zhu.lei
 * @version 1.0
 * @date 2020-07-07 15:53
 */
public class JSONUtil {
    /**
     * 默认的mapper，只配置了如果json中的属性在类中不存在则忽略
     */
    public static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper();
    public static final ObjectMapper SNAKE_CASE_MAPPER = new ObjectMapper();

    public static final ObjectMapper SNAKE_CASE_NO_NULL_MAPPER = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(JSONUtil.class);

    static {
        DEFAULT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    static {
        SNAKE_CASE_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        SNAKE_CASE_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SNAKE_CASE_NO_NULL_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        SNAKE_CASE_NO_NULL_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SNAKE_CASE_NO_NULL_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String objectToJson(Object object) {
        return objectToJson(object, DEFAULT_MAPPER);
    }


    public static String objectToJson(Object object, ObjectMapper mapper) {
        Objects.requireNonNull(object);
        Objects.requireNonNull(mapper);
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("dump object [{}] to json failed, {}", object, e.getMessage());
        }
        return null;
    }

    public static <T> T jsonToObject(String json, Class<T> valueType, ObjectMapper mapper) {
        try {
            return mapper.readValue(json, valueType);
        } catch (JsonProcessingException e) {
            logger.error("parse json {} to object [{}] failed, {}", json, valueType.getName(), e.getMessage());
        }
        return null;
    }
}
