package com.example.auth.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 组织机构树形结构视图对象
 *
 * @author 作者
 * @date 创建时间
 */
@Data
public class OrganizationTreeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    private String id;

    /**
     * 父节点ID
     */
    private String parentId;

    /**
     * 节点名称
     */
    private String label;

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
     * 层级
     */
    private Integer level;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 负责人姓名
     */
    private String leaderName;

    /**
     * 状态（0-禁用，1-正常）
     */
    private Integer status;

    /**
     * 是否禁用（前端element-ui树控件使用）
     */
    private Boolean disabled;

    /**
     * 子节点
     */
    private List<OrganizationTreeVO> children = new ArrayList<>();

    /**
     * 是否有子节点
     */
    private Boolean hasChildren;
} 