package com.example.workflow.model.query;

import lombok.Data;

/**
 * 流程定义查询对象
 *
 * @author Author
 * @version 1.0
 */
@Data
public class ProcessDefinitionQuery {

    /**
     * 流程定义名称
     */
    private String name;

    /**
     * 流程定义KEY
     */
    private String key;

    /**
     * 流程分类
     */
    private String category;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 是否只查询最新版本
     */
    private Boolean latestVersion;

    /**
     * 是否包含图形信息
     */
    private Boolean includeDiagram;

    /**
     * 是否激活
     */
    private Boolean active;

    /**
     * 是否挂起
     */
    private Boolean suspended;

    /**
     * 开始位置
     */
    private Integer startIndex;

    /**
     * 查询记录数
     */
    private Integer pageSize;
} 