package org.bilibili.lottery.common.utils;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;

public class JacksonUtils {
    public static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }
    public static <T> T readValue(String message, Class<T> clazz) throws IOException {
        return mapper.readValue(message, clazz);
    }

    public static <T> T readValue(String message, TypeReference<T> valueTypeRef)throws IOException {
        return mapper.readValue(message, valueTypeRef);
    }

    public static <T> T readValue(String message, JavaType javaType) throws IOException {
        return mapper.readValue(message, javaType);
    }

    public static Object readValue(String message,Class parent,Class child) throws IOException {
        JavaType javaType = getTypeFactory().constructParametricType(parent, child);
        return mapper.readValue(message,javaType);

    }

    public static String writeValue(Object t) throws JsonProcessingException {
        return mapper.writeValueAsString(t);
    }

    public static TypeFactory getTypeFactory(){
        return mapper.getTypeFactory();
    }


}
