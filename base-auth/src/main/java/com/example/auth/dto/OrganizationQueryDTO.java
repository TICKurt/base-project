package com.example.auth.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 组织机构查询条件DTO
 *
 * @author 作者
 * @date 创建时间
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrganizationQueryDTO extends PageDTO {

    /**
     * 组织名称（模糊查询）
     */
    private String name;

    /**
     * 组织编码（精确查询）
     */
    private String code;

    /**
     * 组织类型（1-公司，2-部门，3-团队，4-小组）
     */
    private Integer orgType;

    /**
     * 状态（0-禁用，1-正常）
     */
    private Integer status;

    /**
     * 父级组织ID
     */
    private String parentId;

    /**
     * 负责人姓名（模糊查询）
     */
    private String leaderName;

    /**
     * 联系电话（模糊查询）
     */
    private String contactPhone;

    /**
     * 创建开始时间
     */
    private LocalDateTime beginTime;

    /**
     * 创建结束时间
     */
    private LocalDateTime endTime;
} 