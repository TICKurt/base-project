package com.example.workflow.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 历史任务VO类
 *
 * @author Author
 * @version 1.0
 */
@Data
public class HistoricTaskInstanceVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    private String id;

    /**
     * 流程定义ID
     */
    private String processDefinitionId;

    /**
     * 任务定义KEY
     */
    private String taskDefinitionKey;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 执行实例ID
     */
    private String executionId;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 父任务ID
     */
    private String parentTaskId;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 任务所有者
     */
    private String owner;

    /**
     * 任务办理人
     */
    private String assignee;

    /**
     * 任务办理人名称
     */
    private String assigneeName;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 声明时间
     */
    private Date claimTime;

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
     * 优先级
     */
    private Integer priority;

    /**
     * 到期时间
     */
    private Date dueDate;

    /**
     * 表单KEY
     */
    private String formKey;

    /**
     * 分类
     */
    private String category;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 最后更新时间
     */
    private Date lastUpdatedTime;

    /**
     * 任务变量
     */
    private Map<String, Object> taskLocalVariables;

    /**
     * 流程变量
     */
    private Map<String, Object> processVariables;

    /**
     * 委派状态
     */
    private String delegationState;

//    /**
//     * 跟进日期
//     */
//    private Date followUpDate;

    /**
     * 创建时间
     */
    private Date createTime;
} 