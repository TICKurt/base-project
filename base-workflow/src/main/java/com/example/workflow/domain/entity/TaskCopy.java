package com.example.workflow.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 任务抄送实体
 * 用于支持流程的抄送/知会功能
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wf_task_copy")
public class TaskCopy {

    /**
     * 抄送ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 流程定义ID
     */
    private String processDefinitionId;

    /**
     * 流程名称
     */
    private String processName;

    /**
     * 业务Key
     */
    private String businessKey;

    /**
     * 抄送类型：1-人工抄送，2-规则抄送，3-自动抄送
     */
    private Integer copyType;

    /**
     * 抄送说明
     */
    private String description;

    /**
     * 抄送人ID
     */
    private String copyUserId;

    /**
     * 抄送人名称
     */
    private String copyUserName;

    /**
     * 发送人ID
     */
    private String senderId;

    /**
     * 发送人名称
     */
    private String senderName;

    /**
     * 是否已读：0-未读，1-已读
     */
    private Boolean isRead;

    /**
     * 阅读时间
     */
    private LocalDateTime readTime;

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