package com.example.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 组织关系数据传输对象
 *
 * @author 作者
 * @date 创建时间
 */
@Data
public class SysOrgRelationDTO {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 组织ID
     */
    @NotBlank(message = "组织ID不能为空")
    private String orgId;

    /**
     * 目标组织ID
     */
    @NotBlank(message = "目标组织ID不能为空")
    private String targetOrgId;

    /**
     * 关系类型(1上下级,2同级,3业务协作,4管理关系,5其他)
     */
    @NotNull(message = "关系类型不能为空")
    private Integer relationType;

    /**
     * 关系描述
     */
    @Size(max = 255, message = "关系描述长度不能超过255个字符")
    private String description;

    // 查询参数
    
    /**
     * 组织ID（精确查询）
     */
    private String orgIdFilter;
    
    /**
     * 目标组织ID（精确查询）
     */
    private String targetOrgIdFilter;
    
    /**
     * 关系类型（精确查询）
     */
    private Integer relationTypeFilter;
} 