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
 * 流程设计实体
 * 用于存储流程的设计信息，关联Flowable的流程定义
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "wf_process_design", autoResultMap = true)
public class ProcessDesign {

    /**
     * 流程设计ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 流程编码
     */
    private String processCode;

    /**
     * 流程名称
     */
    private String processName;

    /**
     * 流程KEY（对应Flowable的processDefinitionKey）
     */
    private String processKey;

    /**
     * 流程分类
     */
    private String category;

    /**
     * 流程描述
     */
    private String description;

    /**
     * 表单ID
     */
    private String formId;

    /**
     * 表单类型：1-内部表单，2-外部表单
     */
    private Integer formType;

    /**
     * 外部表单URL
     */
    private String formUrl;

    /**
     * BPMN XML内容
     */
    private String bpmnXml;

    /**
     * 流程图SVG内容
     */
    private String processSvg;

    /**
     * 流程配置数据JSON
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> configJson;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * Flowable流程定义ID
     */
    private String deploymentId;

    /**
     * Flowable流程定义ID
     */
    private String definitionId;

    /**
     * 状态：0-草稿，1-已部署，2-已禁用
     */
    private Integer status;

    /**
     * 是否是主版本
     */
    private Boolean isMain;

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