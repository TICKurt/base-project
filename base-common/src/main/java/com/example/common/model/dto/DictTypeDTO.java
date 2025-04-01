package com.example.common.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 字典类型数据传输对象
 * 用于创建和更新字典类型
 * 
 * @author example
 */
@Data
public class DictTypeDTO {

    /**
     * 主键ID（创建时不需要，更新时必须）
     */
    private String id;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    @Size(max = 100, message = "字典名称长度不能超过100个字符")
    private String name;

    /**
     * 字典编码
     */
    @NotBlank(message = "字典编码不能为空")
    @Size(max = 100, message = "字典编码长度不能超过100个字符")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]*$", message = "字典编码必须以字母开头，且只能包含字母、数字和下划线")
    private String code;

    /**
     * 状态（0-禁用，1-正常）
     */
    private Integer status = 1;

    /**
     * 备注
     */
    @Size(max = 255, message = "备注长度不能超过255个字符")
    private String remark;
} 