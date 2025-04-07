package com.example.workflow.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 流程实例DTO
 *
 * @author Author
 * @version 1.0
 */
@Data
public class ProcessInstanceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 流程实例ID
     */
    private String id;

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
     * 流程定义版本
     */
    private Integer processDefinitionVersion;

    /**
     * 部署ID
     */
    private String deploymentId;

    /**
     * 业务标识
     */
    private String businessKey;

    /**
     * 父流程实例ID
     */
    private String parentId;

    /**
     * 根流程实例ID
     */
    private String rootProcessInstanceId;

    /**
     * 租户ID
     */
    private String tenantId;

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
     * 启动用户ID
     */
    private String startUserId;

    /**
     * 启动用户名称
     */
    private String startUserName;

    /**
     * 是否结束
     */
    private Boolean ended;

    /**
     * 是否挂起
     */
    private Boolean suspended;

    /**
     * 当前活动节点
     */
    private List<String> activeActivityIds;

    /**
     * 流程变量
     */
    private Map<String, Object> variables;

    /**
     * 当前任务列表
     */
    private List<TaskDTO> tasks;
} 