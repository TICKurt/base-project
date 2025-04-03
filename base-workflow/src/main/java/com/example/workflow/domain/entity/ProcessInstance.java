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
 * 流程实例实体
 * 用于扩展Flowable的流程实例，存储业务相关数据
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "wf_process_instance", autoResultMap = true)
public class ProcessInstance {

    /**
     * 流程实例ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * Flowable的流程实例ID
     */
    private String processInstanceId;

    /**
     * 流程设计ID
     */
    private String processDesignId;

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
     * 流程分类
     */
    private String category;

    /**
     * 流程业务Key
     */
    private String businessKey;

    /**
     * 发起人ID
     */
    private String startUserId;

    /**
     * 发起人名称
     */
    private String startUserName;

    /**
     * 发起人部门ID
     */
    private String startDeptId;

    /**
     * 发起人部门名称
     */
    private String startDeptName;

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
     * 当前环节名称
     */
    private String currentActivityName;

    /**
     * 当前环节ID
     */
    private String currentActivityId;

    /**
     * 流程状态：1-进行中，2-已完成，3-已终止，4-已取消
     */
    private Integer status;

    /**
     * 流程完成时间
     */
    private LocalDateTime endTime;

    /**
     * 流程时长（毫秒）
     */
    private Long duration;

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