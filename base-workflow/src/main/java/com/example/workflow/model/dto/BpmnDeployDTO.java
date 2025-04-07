package com.example.workflow.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * BPMN部署DTO - 用于通过JSON方式部署流程
 *
 * @author Author
 * @version 1.0
 */
@Data
public class BpmnDeployDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 流程定义名称
     */
    private String name;

    /**
     * 流程定义分类
     */
    private String category;

    /**
     * BPMN XML内容
     */
    private String bpmnXml;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 资源名称（可选，默认会根据流程名称自动生成）
     */
    private String resourceName;
}