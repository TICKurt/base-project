package com.example.workflow.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 表单字段DTO
 *
 * @author Author
 * @version 1.0
 */
@Data
public class FormFieldDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段标识
     */
    private String fieldKey;

    /**
     * 字段名称
     */
    private String name;

    /**
     * 字段类型
     */
    private String type;

    /**
     * 字段标签
     */
    private String label;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 占位符
     */
    private String placeholder;

    /**
     * 是否必填
     */
    private Boolean required;

    /**
     * 是否禁用
     */
    private Boolean disabled;

    /**
     * 是否只读
     */
    private Boolean readonly;

    /**
     * 最小值
     */
    private String min;

    /**
     * 最大值
     */
    private String max;

    /**
     * 最小长度
     */
    private Integer minLength;

    /**
     * 最大长度
     */
    private Integer maxLength;

    /**
     * 正则表达式
     */
    private String pattern;

    /**
     * 正则表达式提示信息
     */
    private String patternMessage;

    /**
     * 选项列表（适用于下拉框、单选框、复选框等）
     */
    private Map<String, String> options;

    /**
     * 字段配置JSON
     */
    private String configJson;

    /**
     * 排序号
     */
    private Integer sort;
} 