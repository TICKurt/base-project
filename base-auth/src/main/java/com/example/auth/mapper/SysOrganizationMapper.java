package com.example.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.auth.entity.SysOrganization;
import com.example.auth.vo.OrganizationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组织机构Mapper接口
 *
 * @author 作者
 * @date 创建时间
 */
@Mapper
public interface SysOrganizationMapper extends BaseMapper<SysOrganization> {

    /**
     * 分页查询组织机构列表（包含扩展信息）
     *
     * @param page 分页参数
     * @param organization 查询条件
     * @return 分页结果
     */
    IPage<OrganizationVO> selectOrganizationPage(Page<OrganizationVO> page, @Param("org") SysOrganization organization);

    /**
     * 查询组织机构列表（包含扩展信息）
     *
     * @param organization 查询条件
     * @return 组织机构列表
     */
    List<OrganizationVO> selectOrganizationList(@Param("org") SysOrganization organization);

    /**
     * 根据ID查询组织机构详情（包含扩展信息）
     *
     * @param id 组织机构ID
     * @return 组织机构详情
     */
    OrganizationVO selectOrganizationById(@Param("id") String id);

    /**
     * 查询组织机构子节点
     *
     * @param parentId 父级组织ID
     * @return 子节点列表
     */
    List<SysOrganization> selectChildren(@Param("parentId") String parentId);

    /**
     * 根据ID列表批量查询组织机构
     *
     * @param ids ID列表
     * @return 组织机构列表
     */
    List<SysOrganization> selectBatchByIds(@Param("ids") List<String> ids);

    /**
     * 查询组织机构下的用户数量
     *
     * @param orgId 组织机构ID
     * @param includeChildOrg 是否包含子组织
     * @return 用户数量
     */
    int countUserByOrgId(@Param("orgId") String orgId, @Param("includeChildOrg") boolean includeChildOrg);

    /**
     * 查询组织机构的子组织数量
     *
     * @param orgId 组织机构ID
     * @return 子组织数量
     */
    int countChildrenByOrgId(@Param("orgId") String orgId);
} 