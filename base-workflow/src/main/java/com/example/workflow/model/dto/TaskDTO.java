package com.example.workflow.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 任务DTO
 *
 * @author Author
 * @version 1.0
 */
@Data
public class TaskDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    private String id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 任务所有者
     */
    private String owner;

    /**
     * 任务处理人
     */
    private String assignee;

    /**
     * 任务处理人名称
     */
    private String assigneeName;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 流程定义ID
     */
    private String processDefinitionId;

    /**
     * 流程定义名称
     */
    private String processDefinitionName;

    /**
     * 流程定义KEY
     */
    private String processDefinitionKey;

    /**
     * 任务定义KEY
     */
    private String taskDefinitionKey;

    /**
     * 表单KEY
     */
    private String formKey;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 到期时间
     */
    private Date dueDate;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 签收时间
     */
    private Date claimTime;

    /**
     * 任务变量
     */
    private Map<String, Object> variables;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 父任务ID
     */
    private String parentTaskId;

    /**
     * 是否挂起
     */
    private Boolean suspended;

    /**
     * 任务类别
     */
    private String category;

    /**
     * 候选用户列表（逗号分隔）
     */
    private String candidateUsers;

    /**
     * 候选组列表（逗号分隔）
     */
    private String candidateGroups;
} 