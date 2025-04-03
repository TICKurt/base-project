package com.example.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.auth.entity.SysOrgRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组织机构关系Mapper接口
 *
 * @author 作者
 * @date 创建时间
 */
@Mapper
public interface SysOrgRelationMapper extends BaseMapper<SysOrgRelation> {

    /**
     * 批量插入组织机构关系
     *
     * @param relations 关系列表
     * @return 插入数量
     */
    int batchInsert(@Param("list") List<SysOrgRelation> relations);

    /**
     * 根据组织ID删除关系
     *
     * @param orgId 组织ID
     * @return 删除数量
     */
    int deleteByOrgId(@Param("orgId") String orgId);

    /**
     * 根据组织ID查询指定关系类型的所有关系
     *
     * @param orgId 组织ID
     * @param relationType 关系类型
     * @return 关系列表
     */
    List<SysOrgRelation> selectByOrgIdAndType(@Param("orgId") String orgId, @Param("relationType") Integer relationType);

    /**
     * 根据目标组织ID查询指定关系类型的所有关系
     *
     * @param targetOrgId 目标组织ID
     * @param relationType 关系类型
     * @return 关系列表
     */
    List<SysOrgRelation> selectByTargetOrgIdAndType(@Param("targetOrgId") String targetOrgId, @Param("relationType") Integer relationType);
} 