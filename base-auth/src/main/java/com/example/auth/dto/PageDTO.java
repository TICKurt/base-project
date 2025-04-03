package com.example.auth.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询基础DTO
 *
 * @author 作者
 * @date 创建时间
 */
@Data
public class PageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 页码
     */
    private long pageNum = 1;

    /**
     * 每页记录数
     */
    private long pageSize = 10;

    /**
     * 排序字段
     */
    private String orderByColumn;

    /**
     * 排序方向（asc/desc）
     */
    private String orderDirection;
} 