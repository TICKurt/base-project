package com.example.workflow.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 表单规则DTO
 *
 * @author Author
 * @version 1.0
 */
@Data
public class FormRuleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 规则标识
     */
    private String ruleKey;

    /**
     * 规则名称
     */
    private String name;

    /**
     * 规则类型（VALIDATION-验证规则，CALCULATION-计算规则，VISIBILITY-显示规则，LINKAGE-联动规则）
     */
    private String type;

    /**
     * 触发字段
     */
    private String triggerField;

    /**
     * 目标字段列表
     */
    private List<String> targetFields;

    /**
     * 规则条件
     */
    private String condition;

    /**
     * 规则动作
     */
    private String action;

    /**
     * 规则参数
     */
    private Map<String, Object> params;

    /**
     * 规则配置JSON
     */
    private String configJson;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 排序号
     */
    private Integer sort;
} 