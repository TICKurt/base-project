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
import java.util.List;
import java.util.Map;

/**
 * 表单设计实体
 * 用于存储流程表单的设计信息，支持动态表单设计
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "wf_form_design", autoResultMap = true)
public class FormDesign {

    /**
     * 表单ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 表单编码
     */
    private String formCode;

    /**
     * 表单名称
     */
    private String formName;

    /**
     * 表单类型：1-流程表单，2-业务表单
     */
    private Integer formType;

    /**
     * 表单描述
     */
    private String description;

    /**
     * 表单设计JSON数据
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> formJson;

    /**
     * 表单字段配置
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Map<String, Object>> formFields;

    /**
     * 表单样式配置
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> styles;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 状态：0-草稿，1-发布，2-禁用
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