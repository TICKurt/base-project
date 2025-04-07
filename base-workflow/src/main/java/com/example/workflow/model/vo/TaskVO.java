package com.example.workflow.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 任务VO类
 *
 * @author Author
 * @version 1.0
 */
@Data
public class TaskVO implements Serializable {

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
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 流程定义ID
     */
    private String processDefinitionId;

    /**
     * 任务定义标识
     */
    private String taskDefinitionKey;

    /**
     * 表单标识
     */
    private String formKey;

    /**
     * 办理人
     */
    private String assignee;

    /**
     * 所有者
     */
    private String owner;

    /**
     * 委派状态
     */
    private String delegationState;

    /**
     * 到期日期
     */
    private Date dueDate;

//    /**
//     * 跟进日期
//     */
//    private Date followUpDate;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 父任务ID
     */
    private String parentTaskId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 分类
     */
    private String category;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否挂起
     */
    private boolean suspended;

    /**
     * 任务处理人名称
     */
    private String assigneeName;

    /**
     * 流程定义KEY
     */
    private String processDefinitionKey;

    /**
     * 流程定义名称
     */
    private String processDefinitionName;

    /**
     * 业务标识
     */
    private String businessKey;

    /**
     * 创建时间
     */
    private Date claimTime;

    /**
     * 任务变量
     */
    private Map<String, Object> taskVariables;

    /**
     * 流程变量
     */
    private Map<String, Object> processVariables;
} 