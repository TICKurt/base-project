package com.example.workflow.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * 历史活动实例VO
 *
 * @author Author
 * @version 1.0
 */
@Data
public class HistoricActivityInstanceVO {

    /**
     * 活动实例ID
     */
    private String id;

    /**
     * 活动ID
     */
    private String activityId;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动类型
     */
    private String activityType;

    /**
     * 流程定义ID
     */
    private String processDefinitionId;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 执行实例ID
     */
    private String executionId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 被调用的流程实例ID
     */
    private String calledProcessInstanceId;

    /**
     * 办理人
     */
    private String assignee;

    /**
     * 办理人名称
     */
    private String assigneeName;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 持续时间（毫秒）
     */
    private Long durationInMillis;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 删除原因
     */
    private String deleteReason;
} 