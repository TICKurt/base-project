package com.example.workflow.model.dto;

import lombok.Data;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表单设计DTO
 *
 * @author Author
 * @version 1.0
 */
@Data
@Slf4j
public class FormDesignDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 表单标识
     */
    private String formKey;

    /**
     * 表单名称
     */
    private String name;

    /**
     * 表单分类
     */
    private String category;

    /**
     * 表单描述
     */
    private String description;

    /**
     * 表单版本
     */
    private Integer version;

    /**
     * 表单设计JSON
     */
    private String designJson;

    /**
     * 表单配置JSON
     */
    private String configJson;

    /**
     * 表单字段列表
     */
    private List<FormFieldDTO> fields;

    /**
     * 表单规则列表
     */
    private List<FormRuleDTO> rules;

    /**
     * 是否已发布
     */
    private Boolean published;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 创建人ID
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人ID
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 获取表单配置
     * 
     * @return 表单配置
     */
    public Map<String, Object> getConfig() {
        if (configJson == null || configJson.isEmpty()) {
            return new HashMap<>();
        }
        
        try {
            return objectMapper.readValue(configJson, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            log.error("解析表单配置JSON失败: {}", e.getMessage());
            return new HashMap<>();
        }
    }
} 