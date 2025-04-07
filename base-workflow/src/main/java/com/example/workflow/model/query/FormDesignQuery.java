package com.example.workflow.model.query;

import lombok.Data;

/**
 * 表单设计查询对象
 *
 * @author Author
 * @version 1.0
 */
@Data
public class FormDesignQuery {

    /**
     * 表单标识
     */
    private String formKey;

    /**
     * 表单名称
     */
    private String name;

    /**
     * 表单分类
     */
    private String category;

    /**
     * 是否已发布
     */
    private Boolean published;

    /**
     * 创建人ID
     */
    private String createBy;

    /**
     * 创建时间-开始
     */
    private String createTimeStart;

    /**
     * 创建时间-结束
     */
    private String createTimeEnd;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 开始位置
     */
    private Integer startIndex;

    /**
     * 查询记录数
     */
    private Integer pageSize;
} 