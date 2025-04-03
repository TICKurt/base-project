package com.example.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.auth.dto.OrganizationDTO;
import com.example.auth.dto.OrganizationQueryDTO;
import com.example.auth.dto.SysOrganizationDTO;
import com.example.auth.dto.SysOrgRelationDTO;
import com.example.auth.entity.SysOrganization;
import com.example.auth.vo.OrganizationTreeVO;
import com.example.auth.vo.OrganizationVO;
import com.example.auth.vo.SysOrganizationVO;
import com.example.auth.vo.SysOrgRelationVO;

import java.util.List;
import java.util.Map;

/**
 * 组织机构Service接口
 *
 * @author 作者
 * @date 创建时间
 */
public interface SysOrganizationService extends IService<SysOrganization> {

    /**
     * 分页查询组织机构列表
     *
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    IPage<OrganizationVO> pageOrganization(OrganizationQueryDTO queryDTO);

    /**
     * 查询组织机构列表
     *
     * @param queryDTO 查询参数
     * @return 组织机构列表
     */
    List<OrganizationVO> listOrganization(OrganizationQueryDTO queryDTO);

    /**
     * 根据ID查询组织机构详情
     *
     * @param id 组织机构ID
     * @return 组织机构详情
     */
    OrganizationVO getOrganizationById(String id);

    /**
     * 新增组织机构
     *
     * @param dto 组织机构信息
     * @return 新增结果
     */
    OrganizationVO createOrganization(OrganizationDTO dto);

    /**
     * 修改组织机构
     *
     * @param dto 组织机构信息
     * @return 修改结果
     */
    OrganizationVO updateOrganization(OrganizationDTO dto);

    /**
     * 删除组织机构
     *
     * @param id 组织机构ID
     * @return 是否成功
     */
    boolean deleteOrganization(String id);

    /**
     * 批量删除组织机构
     *
     * @param ids 组织机构ID数组
     * @return 是否成功
     */
    boolean batchDeleteOrganization(List<String> ids);

    /**
     * 获取组织机构树
     *
     * @param queryDTO 查询参数
     * @return 组织机构树
     */
    List<OrganizationTreeVO> getOrganizationTree(OrganizationQueryDTO queryDTO);

    /**
     * 获取组织机构的所有子节点ID（包含自身）
     *
     * @param orgId 组织机构ID
     * @return 子节点ID列表
     */
    List<String> getChildrenIds(String orgId);

    /**
     * 检查组织机构编码是否存在
     *
     * @param code 组织机构编码
     * @param id 组织机构ID（更新时使用）
     * @return 是否存在
     */
    boolean checkCodeExists(String code, String id);

    /**
     * 修改组织机构状态
     *
     * @param id 组织机构ID
     * @param status 状态（0-禁用，1-正常）
     * @return 是否成功
     */
    boolean updateStatus(String id, Integer status);

    /**
     * 设置组织关系
     *
     * @param orgId 组织机构ID
     * @param targetOrgIds 目标组织机构ID列表
     * @param relationType 关系类型
     * @return 是否成功
     */
    boolean setOrgRelation(String orgId, List<String> targetOrgIds, Integer relationType);

    /**
     * 查询指定关系类型的目标组织ID列表
     *
     * @param orgId 组织机构ID
     * @param relationType 关系类型
     * @return 目标组织ID列表
     */
    List<String> getRelatedOrgIds(String orgId, Integer relationType);
    
    /**
     * 获取组织机构列表
     *
     * @param dto 查询参数
     * @return 组织机构列表
     */
    List<SysOrganizationVO> getOrgList(SysOrganizationDTO dto);
    
    /**
     * 获取组织机构树形结构
     *
     * @param dto 查询参数
     * @return 组织机构树
     */
    List<SysOrganizationVO> getOrgTree(SysOrganizationDTO dto);
    
    /**
     * 根据ID查询组织机构
     *
     * @param id 组织机构ID
     * @return 组织机构信息
     */
    SysOrganizationVO getOrgById(String id);
    
    /**
     * 新增组织机构
     *
     * @param dto 组织机构信息
     */
    void addOrg(SysOrganizationDTO dto);
    
    /**
     * 修改组织机构
     *
     * @param dto 组织机构信息
     */
    void updateOrg(SysOrganizationDTO dto);
    
    /**
     * 删除组织机构
     *
     * @param id 组织机构ID
     */
    void removeOrg(String id);
    
    /**
     * 获取组织机构树选择列表
     *
     * @param dto 查询参数
     * @return 组织树选择数据
     */
    List<Map<String, Object>> getOrgTreeSelect(SysOrganizationDTO dto);
    
    /**
     * 获取角色组织机构树选择
     *
     * @param roleId 角色ID
     * @return 组织树选择数据和已选择的ID列表
     */
    Map<String, Object> getRoleOrgTreeSelect(String roleId);
    
    /**
     * 获取组织机构详情（包含扩展信息）
     *
     * @param id 组织机构ID
     * @return 组织机构详情
     */
    Map<String, Object> getOrgDetailById(String id);
    
    /**
     * 获取组织机构路径名称
     *
     * @param orgId 组织机构ID
     * @return 路径名称列表
     */
    List<String> getOrgPathNames(String orgId);
    
    /**
     * 检查组织机构编码是否唯一
     *
     * @param id 组织机构ID（更新时使用）
     * @param code 组织机构编码
     * @return 是否唯一
     */
    boolean checkOrgCodeUnique(String id, String code);
    
    /**
     * 获取子组织机构列表
     *
     * @param parentId 父级组织ID
     * @return 子组织列表
     */
    List<SysOrganizationVO> getChildrenOrgs(String parentId);
    
    /**
     * 导出组织机构数据
     *
     * @param dto 查询参数
     * @return 导出的二进制数据
     */
    byte[] exportOrgData(SysOrganizationDTO dto);
    
    /**
     * 获取组织机构扩展信息
     *
     * @param id 组织机构ID
     * @return 组织机构扩展信息
     */
    Map<String, Object> getOrgExtById(String id);
    
    /**
     * 保存组织机构扩展信息
     *
     * @param dto 组织机构信息
     */
    void saveOrgExt(SysOrganizationDTO dto);
    
    /**
     * 更新组织机构扩展信息
     *
     * @param dto 组织机构信息
     */
    void updateOrgExt(SysOrganizationDTO dto);
    
    /**
     * 删除组织机构扩展信息
     *
     * @param id 组织机构ID
     */
    void removeOrgExt(String id);
    
    /**
     * 上传组织机构Logo
     *
     * @param id 组织机构ID
     * @param file Logo文件
     * @return 文件访问URL
     */
    String uploadOrgLogo(String id, Object file);
    
    /**
     * 上传组织机构营业执照
     *
     * @param id 组织机构ID
     * @param file 营业执照文件
     * @return 文件访问URL
     */
    String uploadOrgLicense(String id, Object file);
    
    /**
     * 获取组织关系列表
     *
     * @param dto 查询参数
     * @return 组织关系列表
     */
    List<SysOrgRelationVO> getOrgRelationList(SysOrgRelationDTO dto);
    
    /**
     * 根据ID获取组织关系
     *
     * @param id 组织关系ID
     * @return 组织关系信息
     */
    SysOrgRelationVO getOrgRelationById(String id);
    
    /**
     * 添加组织关系
     *
     * @param dto 组织关系信息
     */
    void addOrgRelation(SysOrgRelationDTO dto);
    
    /**
     * 更新组织关系
     *
     * @param dto 组织关系信息
     */
    void updateOrgRelation(SysOrgRelationDTO dto);
    
    /**
     * 删除组织关系
     *
     * @param id 组织关系ID
     */
    void removeOrgRelation(String id);
    
    /**
     * 获取指定组织的所有关系
     *
     * @param orgId 组织机构ID
     * @return 关系列表
     */
    List<SysOrgRelationVO> getRelationsByOrgId(String orgId);
    
    /**
     * 获取与指定组织有关系的组织列表
     *
     * @param orgId 组织机构ID
     * @param relationType 关系类型
     * @return 关联组织列表
     */
    List<SysOrgRelationVO> getRelatedOrgs(String orgId, Integer relationType);
    
    /**
     * 批量添加组织关系
     *
     * @param relationList 组织关系列表
     */
    void batchAddOrgRelations(List<SysOrgRelationDTO> relationList);
    
    /**
     * 批量删除组织关系
     *
     * @param ids 组织关系ID列表
     */
    void batchRemoveOrgRelations(List<String> ids);
    
    /**
     * 将用户添加到组织
     *
     * @param orgId 组织机构ID
     * @param userIds 用户ID列表
     */
    void addUsersToOrg(String orgId, List<String> userIds);
    
    /**
     * 从组织中移除用户
     *
     * @param orgId 组织机构ID
     * @param userIds 用户ID列表
     */
    void removeUsersFromOrg(String orgId, List<String> userIds);
    
    /**
     * 获取组织负责人ID
     *
     * @param orgId 组织机构ID
     * @return 负责人ID
     */
    String getOrgLeaderId(String orgId);
    
    /**
     * 设置组织负责人
     *
     * @param orgId 组织机构ID
     * @param userId 用户ID
     */
    void setOrgLeader(String orgId, String userId);
} 