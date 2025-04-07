package com.example.workflow.utils;

import com.example.workflow.model.dto.FormDesignDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 表单数据工具类
 *
 * @author Author
 * @version 1.0
 */
@Slf4j
public class FormDataUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 验证表单数据
     *
     * @param formDesign 表单定义
     * @param formData   表单数据
     * @return 错误信息
     */
    public static Map<String, String> validateFormData(FormDesignDTO formDesign, Map<String, Object> formData) {
        Map<String, String> errors = new HashMap<>();

        // 检查表单定义
        if (formDesign == null || formDesign.getFields() == null || formDesign.getFields().isEmpty()) {
            errors.put("form", "表单定义不能为空");
            return errors;
        }

        // 检查表单数据
        if (formData == null) {
            formData = new HashMap<>();
        }

        // 验证每个字段
        Map<String, Object> finalFormData = formData;
        formDesign.getFields().forEach(field -> {
            String fieldName = field.getName();
            Object value = finalFormData.get(fieldName);

            // 验证必填字段
            if (Boolean.TRUE.equals(field.getRequired()) && (value == null || StringUtils.isBlank(String.valueOf(value)))) {
                errors.put(fieldName, "字段不能为空");
                return;
            }

            // 如果值为空，不需要进一步验证
            if (value == null || StringUtils.isBlank(String.valueOf(value))) {
                return;
            }

            // 验证字符串长度
            if (field.getMinLength() != null || field.getMaxLength() != null) {
                String strValue = String.valueOf(value);
                if (field.getMinLength() != null && strValue.length() < field.getMinLength()) {
                    errors.put(fieldName, "字段长度不能小于" + field.getMinLength());
                }
                if (field.getMaxLength() != null && strValue.length() > field.getMaxLength()) {
                    errors.put(fieldName, "字段长度不能大于" + field.getMaxLength());
                }
            }

            // 验证正则表达式
            if (StringUtils.isNotBlank(field.getPattern())) {
                String strValue = String.valueOf(value);
                if (!Pattern.matches(field.getPattern(), strValue)) {
                    errors.put(fieldName, "字段格式不正确");
                }
            }

            // 验证数值范围
            if ("number".equals(field.getType()) || "integer".equals(field.getType())) {
                try {
                    double numValue = Double.parseDouble(String.valueOf(value));
                    if (field.getMinLength() != null && numValue < field.getMinLength()) {
                        errors.put(fieldName, "字段值不能小于" + field.getMinLength());
                    }
                    if (field.getMaxLength() != null && numValue > field.getMaxLength()) {
                        errors.put(fieldName, "字段值不能大于" + field.getMaxLength());
                    }
                } catch (NumberFormatException e) {
                    errors.put(fieldName, "字段值必须是数字");
                }
            }
        });

        return errors;
    }

    /**
     * 获取表单渲染数据
     *
     * @param formDesign 表单定义
     * @param formData   表单数据
     * @return 渲染数据
     */
    public static Map<String, Object> getFormRenderData(FormDesignDTO formDesign, Map<String, Object> formData) {
        Map<String, Object> renderData = new HashMap<>();

        // 设置表单基本信息
        renderData.put("formKey", formDesign.getFormKey());
        renderData.put("name", formDesign.getName());
        renderData.put("description", formDesign.getDescription());
        renderData.put("fields", formDesign.getFields());
        renderData.put("rules", formDesign.getRules());
        renderData.put("config", formDesign.getConfig());

        // 设置表单数据
        renderData.put("formData", formData != null ? formData : new HashMap<>());

        return renderData;
    }

    /**
     * 解析JSON字符串
     *
     * @param json  JSON字符串
     * @param clazz 目标类型
     * @param <T>   泛型类型
     * @return 对象
     */
    public static <T> T parseJson(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }

        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("解析JSON字符串失败", e);
            return null;
        }
    }

    /**
     * 将对象转换为JSON字符串
     *
     * @param object 对象
     * @return JSON字符串
     */
    public static String toJson(Object object) {
        if (object == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("对象转换为JSON字符串失败", e);
            return null;
        }
    }
} 