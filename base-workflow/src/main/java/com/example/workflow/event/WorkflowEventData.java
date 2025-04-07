package com.example.workflow.event;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 工作流事件数据类
 *
 * @author Author
 * @version 1.0
 */
@Data
public class WorkflowEventData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 事件ID
     */
    private String eventId;

    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 事件时间
     */
    private Date eventTime;

    /**
     * 流程定义ID
     */
    private String processDefinitionId;

    /**
     * 流程定义名称
     */
    private String processDefinitionName;

    /**
     * 流程定义版本
     */
    private Integer processDefinitionVersion;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 流程实例名称
     */
    private String processInstanceName;

    /**
     * 业务标识
     */
    private String businessKey;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务定义Key
     */
    private String taskDefinitionKey;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 事件数据
     */
    private Map<String, Object> data;

    /**
     * 构造函数
     */
    public WorkflowEventData() {
        this.eventTime = new Date();
    }

    /**
     * 构造函数
     *
     * @param eventType 事件类型
     */
    public WorkflowEventData(String eventType) {
        this.eventType = eventType;
        this.eventTime = new Date();
    }

    /**
     * 构造函数
     *
     * @param eventType 事件类型
     * @param data      事件数据
     */
    public WorkflowEventData(String eventType, Map<String, Object> data) {
        this.eventType = eventType;
        this.data = data;
        this.eventTime = new Date();
    }

    /**
     * 构造函数
     *
     * @param eventType           事件类型
     * @param processDefinitionId 流程定义ID
     * @param processInstanceId   流程实例ID
     * @param taskId             任务ID
     * @param businessKey        业务标识
     * @param userId             用户ID
     * @param data              事件数据
     */
    public WorkflowEventData(String eventType, String processDefinitionId, String processInstanceId,
                           String taskId, String businessKey, String userId, Map<String, Object> data) {
        this.eventType = eventType;
        this.processDefinitionId = processDefinitionId;
        this.processInstanceId = processInstanceId;
        this.taskId = taskId;
        this.businessKey = businessKey;
        this.userId = userId;
        this.data = data;
        this.eventTime = new Date();
    }
} 