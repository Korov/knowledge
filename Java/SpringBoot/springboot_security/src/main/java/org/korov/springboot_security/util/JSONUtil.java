package org.korov.springboot_security.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * json与类互相转换工具
 *
 * @author zhu.lei
 * @version 1.0
 * @date 2020-07-07 15:53
 */
public class JSONUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Object转json string
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static String objectToJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    /**
     * 将json String转换为Map
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    public static Map jsonToMap(String json) throws JsonProcessingException {
        return jsonToObject(json, Map.class);
    }

    public static <T> T jsonToObject(String json, Class<T> valueType) throws JsonProcessingException {
        return mapper.readValue(json, valueType);
    }

    public static JsonNode jsonToNode(String json) throws JsonProcessingException {
        return mapper.readTree(json);
    }
}
