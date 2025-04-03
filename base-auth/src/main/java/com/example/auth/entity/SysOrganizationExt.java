package com.example.auth.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 组织机构扩展信息实体类
 *
 * @author 作者
 * @date 创建时间
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_organization_ext")
public class SysOrganizationExt implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID（与组织表ID相同）
     */
    @TableId
    private String id;

    /**
     * 统一社会信用代码
     */
    private String creditCode;

    /**
     * 法人代表
     */
    private String legalPerson;

    /**
     * 营业执照URL
     */
    private String businessLicense;

    /**
     * 注册资本
     */
    private BigDecimal registeredCapital;

    /**
     * 成立时间
     */
    private LocalDate foundedTime;

    /**
     * 经营范围
     */
    private String businessScope;

    /**
     * 所属区域ID
     */
    private String regionId;

    /**
     * 详细地址
     */
    private String detailedAddress;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 传真
     */
    private String fax;

    /**
     * 网站
     */
    private String website;

    /**
     * Logo URL
     */
    private String logo;

    /**
     * 与上级关系类型(1直属,2控股,3参股,4合作,5其他)
     */
    private Integer parentRelationType;

    /**
     * 组织类别(如生产部门、管理部门、营销部门等)
     */
    private String orgCategory;

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
     * 租户ID
     */
    private String tenantId;
} 