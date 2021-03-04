package com.iquantex.paas.nacostest.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

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

}
