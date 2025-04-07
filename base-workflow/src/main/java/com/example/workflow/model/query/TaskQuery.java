package com.example.workflow.model.query;

import lombok.Data;

/**
 * 任务查询对象
 *
 * @author Author
 * @version 1.0
 */
@Data
public class TaskQuery {

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务处理人
     */
    private String assignee;

    /**
     * 候选用户
     */
    private String candidateUser;

    /**
     * 候选组
     */
    private String candidateGroup;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 流程定义ID
     */
    private String processDefinitionId;

    /**
     * 流程定义KEY
     */
    private String processDefinitionKey;

    /**
     * 业务标识
     */
    private String businessKey;

    /**
     * 是否包含流程变量
     */
    private Boolean includeProcessVariables;

    /**
     * 是否包含任务变量
     */
    private Boolean includeTaskLocalVariables;

    /**
     * 是否已完成
     */
    private Boolean finished;

    /**
     * 是否未完成
     */
    private Boolean unfinished;

    /**
     * 创建时间-开始
     */
    private String createTimeStart;

    /**
     * 创建时间-结束
     */
    private String createTimeEnd;

    /**
     * 到期时间-开始
     */
    private String dueDateStart;

    /**
     * 到期时间-结束
     */
    private String dueDateEnd;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 开始位置
     */
    private Integer startIndex;

    /**
     * 查询记录数
     */
    private Integer pageSize;
} 