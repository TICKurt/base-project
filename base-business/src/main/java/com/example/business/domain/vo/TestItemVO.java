package com.example.business.domain.vo;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 测试项目视图对象
 * 用于返回给前端
 * 
 * @author example
 */
@Data
public class TestItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目描述
     */
    private String description;

    /**
     * 创建人ID
     */
    private Integer createdBy;
    
    /**
     * 创建人名称
     */
    private String creatorName;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
} 