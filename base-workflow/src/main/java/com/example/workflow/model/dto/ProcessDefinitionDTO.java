package com.example.workflow.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程定义DTO
 *
 * @author Author
 * @version 1.0
 */
@Data
public class ProcessDefinitionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 流程定义ID
     */
    private String id;

    /**
     * 流程定义名称
     */
    private String name;

    /**
     * 流程定义KEY
     */
    private String key;

    /**
     * 流程定义版本
     */
    private Integer version;

    /**
     * 部署ID
     */
    private String deploymentId;

    /**
     * 流程分类
     */
    private String category;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 流程图资源名称
     */
    private String diagramResourceName;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否挂起
     */
    private Boolean suspended;

    /**
     * 部署时间
     */
    private Date deploymentTime;

    /**
     * 表单Key
     */
    private String formKey;

    /**
     * 是否有开始表单
     */
    private Boolean hasStartForm;
} 