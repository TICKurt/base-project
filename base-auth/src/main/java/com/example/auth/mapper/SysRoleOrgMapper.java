package com.example.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.auth.domain.entity.SysRoleOrg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色组织关联数据访问接口
 *
 * @author example
 * @date 2023-04-01
 */
@Mapper
public interface SysRoleOrgMapper extends BaseMapper<SysRoleOrg> {

    /**
     * 批量新增角色组织关联
     *
     * @param roleId   角色ID
     * @param orgIds   组织ID列表
     * @param createBy 创建人
     * @param tenantId 租户ID
     * @return 插入条数
     */
    int batchInsert(@Param("roleId") String roleId, @Param("orgIds") List<String> orgIds,
                    @Param("createBy") String createBy, @Param("tenantId") String tenantId);

    /**
     * 根据角色ID删除角色组织关联
     *
     * @param roleId 角色ID
     * @return 删除条数
     */
    int deleteByRoleId(@Param("roleId") String roleId);

    /**
     * 根据组织ID删除角色组织关联
     *
     * @param orgId 组织ID
     * @return 删除条数
     */
    int deleteByOrgId(@Param("orgId") String orgId);

    /**
     * 获取角色关联的组织ID列表
     *
     * @param roleId 角色ID
     * @return 组织ID列表
     */
    List<String> selectOrgIdsByRoleId(@Param("roleId") String roleId);
    
    /**
     * 检查角色是否关联某组织
     *
     * @param roleId 角色ID
     * @param orgId  组织ID
     * @return 关联数量
     */
    int checkRoleOrg(@Param("roleId") String roleId, @Param("orgId") String orgId);
    
    /**
     * 根据用户ID获取自定义数据权限的组织ID列表
     * 
     * @param userId 用户ID
     * @return 组织ID列表
     */
    List<String> selectDataScopeOrgIdsByUserId(@Param("userId") String userId);

    /**
     * 获取指定组织机构的所有子机构ID列表
     *
     * @param orgId 组织机构ID
     * @return 子机构ID列表
     */
    List<String> selectChildOrgIds(@Param("orgId") String orgId);
} 