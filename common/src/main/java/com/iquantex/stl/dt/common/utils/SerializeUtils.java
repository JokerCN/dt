package com.iquantex.stl.dt.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.iquantex.stl.dt.common.exceptions.DtException;
import lombok.SneakyThrows;

import java.io.IOException;

public class SerializeUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public static String toJson(Object obj){
        return objectMapper.writeValueAsString(obj);
    }

    @SneakyThrows
    public static <T> T fromJson(String json, Class<T> clazz){
        return objectMapper.readValue(json, clazz);
    }

    public static ObjectNode createEmptyJsonNode() {
        return new ObjectNode(objectMapper.getNodeFactory());
    }

    public static ArrayNode createEmptyArrayNode(){
        return new ArrayNode(objectMapper.getNodeFactory());
    }
    /**
     * Json string deserialize to Jackson {@link JsonNode}.
     *
     * @param json json string
     * @return {@link JsonNode}
     * @throws DtException if deserialize failed
     */
    public static JsonNode toObj(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (IOException e) {
            throw new DtException(e);
        }
    }
}
