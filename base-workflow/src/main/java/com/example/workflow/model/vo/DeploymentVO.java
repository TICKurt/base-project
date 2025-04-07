package com.example.workflow.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * 部署VO
 *
 * @author Author
 * @version 1.0
 */
@Data
public class DeploymentVO {

    /**
     * 部署ID
     */
    private String id;

    /**
     * 部署名称
     */
    private String name;

    /**
     * 部署分类
     */
    private String category;

    /**
     * 部署KEY
     */
    private String key;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 部署时间
     */
    private Date deploymentTime;

    /**
     * 引擎版本
     */
    private String engineVersion;

    /**
     * 是否新版本
     */
    private Boolean isNew;

    /**
     * 资源列表
     */
    private String[] resources;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 部署来源
     */
    private String source;

    /**
     * 部署描述
     */
    private String description;
} 