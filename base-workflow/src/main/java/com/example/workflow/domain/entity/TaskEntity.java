package com.example.workflow.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 任务实体
 * 用于扩展Flowable的Task任务，存储业务相关数据
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "wf_task", autoResultMap = true)
public class TaskEntity {

    /**
     * 任务ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * Flowable任务ID
     */
    private String taskId;

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
     * 流程名称
     */
    private String processName;

    /**
     * 业务Key
     */
    private String businessKey;

    /**
     * 任务节点定义Key
     */
    private String taskDefinitionKey;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务类型：1-审批，2-填写，3-会签，4-或签，5-抄送
     */
    private Integer taskType;

    /**
     * 表单ID
     */
    private String formId;

    /**
     * 表单数据（JSON格式）
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> formData;

    /**
     * 审批意见
     */
    private String comment;

    /**
     * 审批结果：1-同意，2-拒绝，3-转交，4-驳回，5-撤回
     */
    private Integer approvalResult;

    /**
     * 处理时间
     */
    private LocalDateTime completeTime;

    /**
     * 处理人ID
     */
    private String assigneeId;

    /**
     * 处理人名称
     */
    private String assigneeName;

    /**
     * 候选人IDs（JSON数组）
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private String[] candidateIds;

    /**
     * 候选人组IDs（JSON数组）
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private String[] candidateGroupIds;

    /**
     * 转交前处理人
     */
    private String previousAssigneeId;

    /**
     * 期限
     */
    private LocalDateTime dueDate;

    /**
     * 优先级：0-普通，1-重要，2-紧急
     */
    private Integer priority;

    /**
     * 是否已催办：0-否，1-是
     */
    private Boolean reminded;

    /**
     * 最近催办时间
     */
    private LocalDateTime lastReminderTime;

    /**
     * 任务状态：1-待处理，2-已完成，3-已转交，4-已取消
     */
    private Integer status;

    /**
     * 任务配置数据
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> taskConfig;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;
} 