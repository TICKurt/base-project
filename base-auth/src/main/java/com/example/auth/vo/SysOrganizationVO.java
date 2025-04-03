package com.example.auth.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 组织机构视图对象
 *
 * @author 作者
 * @date 创建时间
 */
@Data
public class SysOrganizationVO {

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
    private String name;

    /**
     * 组织编码
     */
    private String code;

    /**
     * 组织类型（1-公司，2-部门，3-团队，4-小组）
     */
    private Integer orgType;
    
    /**
     * 组织类型名称
     */
    private String orgTypeName;

    /**
     * 组织层级
     */
    private Integer level;

    /**
     * 组织路径（存储所有父级ID，格式如：/根ID/父ID/）
     */
    private String path;

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
    private String contactPhone;

    /**
     * 联系地址
     */
    private String contactAddress;

    /**
     * 状态（0-禁用，1-正常）
     */
    private Integer status;
    
    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 子组织列表
     */
    private List<SysOrganizationVO> children;

    /**
     * 是否有子节点
     */
    private Boolean hasChildren;
    
    /**
     * 上级组织名称
     */
    private String parentName;

    // 组织机构扩展信息字段

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
    @JsonFormat(pattern = "yyyy-MM-dd")
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
     * 所属区域名称
     */
    private String regionName;

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
     * 与上级关系类型名称
     */
    private String parentRelationTypeName;

    /**
     * 组织类别(如生产部门、管理部门、营销部门等)
     */
    private String orgCategory;
    
    /**
     * 用户数量（该组织下的用户数）
     */
    private Integer userCount;
    
    /**
     * 下级组织数量
     */
    private Integer childCount;
} 