package com.example.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 组织机构数据传输对象
 *
 * @author 作者
 * @date 创建时间
 */
@Data
public class SysOrganizationDTO {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 父级组织ID
     */
    private String parentId;

    /**
     * 组织名称
     */
    @NotBlank(message = "组织名称不能为空")
    @Size(max = 100, message = "组织名称长度不能超过100个字符")
    private String name;

    /**
     * 组织编码
     */
    @Size(max = 50, message = "组织编码长度不能超过50个字符")
    private String code;

    /**
     * 组织类型（1-公司，2-部门，3-团队，4-小组）
     */
    @NotNull(message = "组织类型不能为空")
    private Integer orgType;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 负责人ID
     */
    private String leaderId;

    /**
     * 负责人姓名
     */
    private String leaderName;

    /**
     * 联系电话
     */
    @Size(max = 20, message = "联系电话长度不能超过20个字符")
    private String contactPhone;

    /**
     * 联系地址
     */
    @Size(max = 255, message = "联系地址长度不能超过255个字符")
    private String contactAddress;

    /**
     * 状态（0-禁用，1-正常）
     */
    private Integer status;

    /**
     * 备注
     */
    @Size(max = 255, message = "备注长度不能超过255个字符")
    private String remark;

    // 组织机构扩展信息字段

    /**
     * 统一社会信用代码
     */
    @Size(max = 50, message = "统一社会信用代码长度不能超过50个字符")
    private String creditCode;

    /**
     * 法人代表
     */
    @Size(max = 50, message = "法人代表长度不能超过50个字符")
    private String legalPerson;

    /**
     * 营业执照URL
     */
    @Size(max = 255, message = "营业执照URL长度不能超过255个字符")
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
    @Size(max = 255, message = "详细地址长度不能超过255个字符")
    private String detailedAddress;

    /**
     * 邮箱
     */
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    private String email;

    /**
     * 传真
     */
    @Size(max = 30, message = "传真长度不能超过30个字符")
    private String fax;

    /**
     * 网站
     */
    @Size(max = 255, message = "网站长度不能超过255个字符")
    private String website;

    /**
     * Logo URL
     */
    @Size(max = 255, message = "Logo URL长度不能超过255个字符")
    private String logo;

    /**
     * 与上级关系类型(1直属,2控股,3参股,4合作,5其他)
     */
    private Integer parentRelationType;

    /**
     * 组织类别(如生产部门、管理部门、营销部门等)
     */
    @Size(max = 50, message = "组织类别长度不能超过50个字符")
    private String orgCategory;

    // 查询参数

    /**
     * 组织名称（模糊查询）
     */
    private String nameLike;

    /**
     * 组织状态（精确查询）
     */
    private Integer statusFilter;

    /**
     * 组织类型（精确查询）
     */
    private Integer orgTypeFilter;

    /**
     * 是否包含下级
     */
    private Boolean includeChildren;

    /**
     * 是否只查询顶级
     */
    private Boolean onlyRoot;

    /**
     * 是否获取计算字段（children和hasChildren）
     */
    private Boolean withCalcFields;
} 