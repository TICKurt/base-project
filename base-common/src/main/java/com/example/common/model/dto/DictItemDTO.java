package com.example.common.model.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 字典项数据传输对象
 * 用于创建和更新字典项
 * 
 * @author example
 */
@Data
public class DictItemDTO {

    /**
     * 主键ID（创建时不需要，更新时必须）
     */
    private String id;

    /**
     * 字典类型ID
     */
    @NotBlank(message = "字典类型ID不能为空")
    private String dictTypeId;

    /**
     * 字典标签
     */
    @NotBlank(message = "字典标签不能为空")
    @Size(max = 100, message = "字典标签长度不能超过100个字符")
    private String label;

    /**
     * 字典值
     */
    @NotBlank(message = "字典值不能为空")
    @Size(max = 100, message = "字典值长度不能超过100个字符")
    private String value;

    /**
     * 样式属性（其他样式扩展）
     */
    @Size(max = 100, message = "样式属性长度不能超过100个字符")
    private String cssClass;

    /**
     * 表格回显样式
     */
    @Size(max = 100, message = "表格回显样式长度不能超过100个字符")
    private String listClass;

    /**
     * 是否默认（0-否，1-是）
     */
    @Min(value = 0, message = "是否默认值必须是0或1")
    @Max(value = 1, message = "是否默认值必须是0或1")
    private Integer isDefault = 0;

    /**
     * 状态（0-禁用，1-正常）
     */
    @Min(value = 0, message = "状态值必须是0或1")
    @Max(value = 1, message = "状态值必须是0或1")
    private Integer status = 1;

    /**
     * 排序号
     */
    @Min(value = 0, message = "排序号必须大于等于0")
    private Integer sort = 0;

    /**
     * 备注
     */
    @Size(max = 255, message = "备注长度不能超过255个字符")
    private String remark;
} 