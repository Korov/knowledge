package org.korov.flink.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JSONUtils {
    /**
     * 默认的mapper，只配置了如果json中的属性在类中不存在则忽略
     */
    public static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper();
    public static final ObjectMapper SNAKE_CASE_MAPPER = new ObjectMapper();

    public static final ObjectMapper SNAKE_CASE_NO_NULL_MAPPER = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(JSONUtils.class);

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

    public static String objectToJsonNoException(Object object) {
        return objectToJsonNoException(object, DEFAULT_MAPPER);
    }

    public static byte[] objectToBytesNoException(Object object, ObjectMapper mapper) {
        String json = objectToJsonNoException(object, mapper);
        return json != null ? json.getBytes(StandardCharsets.UTF_8) : null;
    }

    public static String objectToJsonNoException(Object object, ObjectMapper mapper) {
        Objects.requireNonNull(object);
        Objects.requireNonNull(mapper);
        try {
            return objectToJson(object, mapper);
        } catch (JsonProcessingException e) {
            logger.error("dump object [{}] to json failed, {}", object, e.getMessage());
        }
        return null;
    }

    /**
     * Object转json string
     *
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static String objectToJson(Object object, ObjectMapper mapper) throws JsonProcessingException {
        Objects.requireNonNull(object);
        Objects.requireNonNull(mapper);
        return mapper.writeValueAsString(object);
    }

    /**
     * 将json String转换为Map
     *
     * @param json
     * @return Map if succ, otherwise null
     */
    public static Map jsonToMap(String json) throws JsonProcessingException {
        return jsonToObject(json, Map.class, DEFAULT_MAPPER);
    }

    /**
     * 将json String转换为Map
     *
     * @param json
     * @return Map if succ, otherwise null
     */
    public static Map jsonToMapNoException(String json) {
        try {
            return jsonToObject(json, Map.class, DEFAULT_MAPPER);
        } catch (JsonProcessingException e) {
            logger.error("parse json {} to Map failed, {}", json, e.getMessage());
        }
        return null;
    }

    public static <T> T jsonToObjectNoException(String json, Class<T> valueType) {
        try {
            return jsonToObject(json, valueType, DEFAULT_MAPPER);
        } catch (JsonProcessingException e) {
            logger.error("parse json {} to object [{}] failed, {}", json, valueType.getName(), e.getMessage());
        }
        return null;
    }


    public static <T> T jsonToObjectNoException(String json, Class<T> valueType, ObjectMapper mapper) {
        try {
            return jsonToObject(json, valueType, mapper);
        } catch (JsonProcessingException e) {
            logger.error("parse json {} to object [{}] failed, {}", json, valueType.getName(), e.getMessage());
        }
        return null;
    }

    public static <T> T jsonToObject(String json, Class<T> valueType, ObjectMapper mapper) throws JsonProcessingException {
        Objects.requireNonNull(json);
        Objects.requireNonNull(valueType);
        Objects.requireNonNull(mapper);
        return mapper.readValue(json, valueType);
    }

    /**
     * 将json String转换为JsonNode
     *
     * @param json
     * @return JsonNode if succ, otherwise null
     */
    public static JsonNode jsonToNodeNoException(String json) {
        try {
            return jsonToNode(json, DEFAULT_MAPPER);
        } catch (JsonProcessingException e) {
            logger.error("parse json {} to json node failed, {}", json, e.getMessage());
        }
        return null;
    }

    public static JsonNode jsonToNodeNoException(String json, ObjectMapper mapper) {
        try {
            return jsonToNode(json, mapper);
        } catch (JsonProcessingException e) {
            logger.error("parse json {} to JsonNode failed, {}", json, e.getMessage());
        }
        return null;
    }

    public static JsonNode jsonToNode(String json, ObjectMapper mapper) throws JsonProcessingException {
        Objects.requireNonNull(json);
        Objects.requireNonNull(mapper);
        return mapper.readTree(json);
    }

    public static List jsonToList(String json) throws JsonProcessingException {
        return jsonToObject(json, List.class, DEFAULT_MAPPER);
    }

    /**
     * 将json String转换为List
     *
     * @param json
     * @return List if succ, otherwise null
     */
    public static List jsonToListNoException(String json) {
        try {
            return jsonToList(json);
        } catch (JsonProcessingException e) {
            logger.error("parse json {} to List failed, {}", json, e.getMessage());
        }
        return null;
    }

    public static String getStringFromNode(JsonNode jsonNode, String key) {
        if (jsonNode.get(key) == null) {
            return null;
        } else {
            return jsonNode.get(key).textValue();
        }
    }

    public static Long getLongFromNode(JsonNode jsonNode, String key) {
        if (jsonNode.get(key) == null) {
            return null;
        } else {
            return jsonNode.get(key).longValue();
        }
    }
}
