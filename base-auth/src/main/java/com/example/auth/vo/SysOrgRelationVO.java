package com.example.auth.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 组织关系视图对象
 *
 * @author 作者
 * @date 创建时间
 */
@Data
public class SysOrgRelationVO {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 组织ID
     */
    private String orgId;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 组织编码
     */
    private String orgCode;

    /**
     * 目标组织ID
     */
    private String targetOrgId;

    /**
     * 目标组织名称
     */
    private String targetOrgName;

    /**
     * 目标组织编码
     */
    private String targetOrgCode;

    /**
     * 关系类型（1：上下级，2：同级，3：业务协作，4：管理关系，5：其他）
     */
    private Integer relationType;

    /**
     * 关系类型名称
     */
    private String relationTypeName;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 租户ID
     */
    private String tenantId;
} 