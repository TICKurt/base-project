package com.example.workflow.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 流程实例VO类
 *
 * @author Author
 * @version 1.0
 */
@Data
public class ProcessInstanceVO implements Serializable {

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
     * 流程定义标识
     */
    private String processDefinitionKey;

    /**
     * 流程定义名称
     */
    private String processDefinitionName;

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
     * 流程实例名称
     */
    private String name;

    /**
     * 流程实例描述
     */
    private String description;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 开始用户ID
     */
    private String startUserId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 是否挂起
     */
    private boolean suspended;

    /**
     * 是否结束
     */
    private boolean ended;

    /**
     * 流程发起人名称
     */
    private String startUserName;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 持续时间（毫秒）
     */
    private Long durationInMillis;

    /**
     * 删除原因
     */
    private String deleteReason;

    /**
     * 父流程实例ID
     */
    private String parentId;

    /**
     * 根流程实例ID
     */
    private String rootProcessInstanceId;

    /**
     * 当前活动ID
     */
    private String activityId;

    /**
     * 当前活动名称
     */
    private String activityName;

    /**
     * 流程变量
     */
    private Map<String, Object> processVariables;
} 