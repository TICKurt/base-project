package com.example.workflow.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 流程变量工具类
 *
 * @author Author
 * @version 1.0
 */
@Slf4j
public class VariableUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将对象转换为流程变量
     *
     * @param object 对象
     * @return 流程变量
     */
    public static Map<String, Object> toVariables(Object object) {
        if (object == null) {
            return new HashMap<>();
        }

        try {
            // 将对象转换为JSON字符串
            String json = objectMapper.writeValueAsString(object);
            // 将JSON字符串转换为Map
            return objectMapper.readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            log.error("对象转换为流程变量失败", e);
            return new HashMap<>();
        }
    }

    /**
     * 将流程变量转换为对象
     *
     * @param variables 流程变量
     * @param clazz     目标类型
     * @param <T>       泛型类型
     * @return 对象
     */
    public static <T> T toObject(Map<String, Object> variables, Class<T> clazz) {
        if (variables == null || variables.isEmpty()) {
            return null;
        }

        try {
            // 将Map转换为JSON字符串
            String json = objectMapper.writeValueAsString(variables);
            // 将JSON字符串转换为对象
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("流程变量转换为对象失败", e);
            return null;
        }
    }

    /**
     * 获取字符串类型的流程变量
     *
     * @param variables 流程变量
     * @param key      变量名
     * @return 字符串值
     */
    public static String getString(Map<String, Object> variables, String key) {
        if (variables == null || StringUtils.isBlank(key)) {
            return null;
        }

        Object value = variables.get(key);
        return value != null ? String.valueOf(value) : null;
    }

    /**
     * 获取整数类型的流程变量
     *
     * @param variables 流程变量
     * @param key      变量名
     * @return 整数值
     */
    public static Integer getInteger(Map<String, Object> variables, String key) {
        if (variables == null || StringUtils.isBlank(key)) {
            return null;
        }

        Object value = variables.get(key);
        if (value == null) {
            return null;
        }

        try {
            return Integer.parseInt(String.valueOf(value));
        } catch (NumberFormatException e) {
            log.error("流程变量转换为整数失败", e);
            return null;
        }
    }

    /**
     * 获取长整数类型的流程变量
     *
     * @param variables 流程变量
     * @param key      变量名
     * @return 长整数值
     */
    public static Long getLong(Map<String, Object> variables, String key) {
        if (variables == null || StringUtils.isBlank(key)) {
            return null;
        }

        Object value = variables.get(key);
        if (value == null) {
            return null;
        }

        try {
            return Long.parseLong(String.valueOf(value));
        } catch (NumberFormatException e) {
            log.error("流程变量转换为长整数失败", e);
            return null;
        }
    }

    /**
     * 获取布尔类型的流程变量
     *
     * @param variables 流程变量
     * @param key      变量名
     * @return 布尔值
     */
    public static Boolean getBoolean(Map<String, Object> variables, String key) {
        if (variables == null || StringUtils.isBlank(key)) {
            return null;
        }

        Object value = variables.get(key);
        if (value == null) {
            return null;
        }

        if (value instanceof Boolean) {
            return (Boolean) value;
        }

        String strValue = String.valueOf(value);
        return "true".equalsIgnoreCase(strValue) || "1".equals(strValue);
    }

    /**
     * 获取双精度浮点数类型的流程变量
     *
     * @param variables 流程变量
     * @param key      变量名
     * @return 双精度浮点数值
     */
    public static Double getDouble(Map<String, Object> variables, String key) {
        if (variables == null || StringUtils.isBlank(key)) {
            return null;
        }

        Object value = variables.get(key);
        if (value == null) {
            return null;
        }

        try {
            return Double.parseDouble(String.valueOf(value));
        } catch (NumberFormatException e) {
            log.error("流程变量转换为双精度浮点数失败", e);
            return null;
        }
    }

    /**
     * 获取Map类型的流程变量
     *
     * @param variables 流程变量
     * @param key      变量名
     * @return Map值
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMap(Map<String, Object> variables, String key) {
        if (variables == null || StringUtils.isBlank(key)) {
            return new HashMap<>();
        }

        Object value = variables.get(key);
        if (value == null) {
            return new HashMap<>();
        }

        if (value instanceof Map) {
            return (Map<String, Object>) value;
        }

        try {
            // 将值转换为JSON字符串
            String json = objectMapper.writeValueAsString(value);
            // 将JSON字符串转换为Map
            return objectMapper.readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            log.error("流程变量转换为Map失败", e);
            return new HashMap<>();
        }
    }
} 