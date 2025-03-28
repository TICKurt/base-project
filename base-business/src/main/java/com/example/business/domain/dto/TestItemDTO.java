package com.example.business.domain.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 测试项目数据传输对象
 * 用于接收前端请求
 * 
 * @author example
 */
@Data
public class TestItemDTO {

    /**
     * 主键ID（新增时不需要传，修改时需要传）
     */
    private Integer id;

    /**
     * 项目名称（必填）
     */
    @NotBlank(message = "项目名称不能为空")
    @Size(min = 2, max = 100, message = "项目名称长度必须在2-100个字符之间")
    private String name;

    /**
     * 项目描述（选填）
     */
    private String description;
} 