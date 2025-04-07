package com.example.workflow.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程定义VO类
 *
 * @author Author
 * @version 1.0
 */
@Data
public class ProcessDefinitionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 流程定义ID
     */
    private String id;

    /**
     * 流程定义标识
     */
    private String key;

    /**
     * 流程定义名称
     */
    private String name;

    /**
     * 流程定义分类
     */
    private String category;

    /**
     * 流程定义版本
     */
    private Integer version;

    /**
     * 流程定义描述
     */
    private String description;

    /**
     * 部署ID
     */
    private String deploymentId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 流程图资源名称
     */
    private String diagramResourceName;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 是否挂起
     */
    private boolean suspended;

    /**
     * 部署时间
     */
    private Date deploymentTime;

    /**
     * 是否有启动表单
     */
    private Boolean hasStartForm;

    /**
     * 是否有图形化信息
     */
    private Boolean hasGraphicalNotation;
} 