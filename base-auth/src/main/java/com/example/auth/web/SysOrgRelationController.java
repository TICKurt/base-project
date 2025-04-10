package com.example.auth.web;

import com.example.auth.dto.SysOrgRelationDTO;
import com.example.auth.entity.SysOrgRelation;
import com.example.auth.service.SysOrganizationService;
import com.example.auth.vo.SysOrgRelationVO;
import com.example.auth.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 组织关系管理控制器
 *
 * @author 作者
 * @date 创建时间
 */
@RestController
@RequestMapping("/organization/relation")
public class SysOrgRelationController {

    @Autowired
    private SysOrganizationService organizationService;

    /**
     * 获取组织关系列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('system:org:list')")
    public Result<List<SysOrgRelationVO>> list(SysOrgRelationDTO dto) {
        List<SysOrgRelationVO> list = organizationService.getOrgRelationList(dto);
        return Result.ok(list);
    }

    /**
     * 获取组织关系详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:org:query')")
    public Result<SysOrgRelationVO> getInfo(@PathVariable String id) {
        SysOrgRelationVO relation = organizationService.getOrgRelationById(id);
        return Result.ok(relation);
    }

    /**
     * 新增组织关系
     */
    @PostMapping
    @PreAuthorize("hasAuthority('system:org:edit')")
    public Result<Void> add(@Valid @RequestBody SysOrgRelationDTO dto) {
        organizationService.addOrgRelation(dto);
        return Result.ok();
    }

    /**
     * 修改组织关系
     */
    @PutMapping
    @PreAuthorize("hasAuthority('system:org:edit')")
    public Result<Void> update(@Valid @RequestBody SysOrgRelationDTO dto) {
        organizationService.updateOrgRelation(dto);
        return Result.ok();
    }

    /**
     * 删除组织关系
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:org:remove')")
    public Result<Void> remove(@PathVariable String id) {
        organizationService.removeOrgRelation(id);
        return Result.ok();
    }

    /**
     * 获取指定组织的关系列表
     */
    @GetMapping("/org/{orgId}")
    @PreAuthorize("hasAuthority('system:org:query')")
    public Result<List<SysOrgRelationVO>> getRelationsByOrgId(@PathVariable String orgId) {
        List<SysOrgRelationVO> relations = organizationService.getRelationsByOrgId(orgId);
        return Result.ok(relations);
    }

    /**
     * 获取指定组织的关联组织列表
     */
    @GetMapping("/related/{orgId}")
    @PreAuthorize("hasAuthority('system:org:query')")
    public Result<List<SysOrgRelationVO>> getRelatedOrgs(@PathVariable String orgId, 
                                                                @RequestParam(required = false) Integer relationType) {
        List<SysOrgRelationVO> relatedOrgs = organizationService.getRelatedOrgs(orgId, relationType);
        return Result.ok(relatedOrgs);
    }

    /**
     * 批量添加组织关系
     */
    @PostMapping("/batch")
    @PreAuthorize("hasAuthority('system:org:edit')")
    public Result<Void> batchAddRelations(@Valid @RequestBody List<SysOrgRelationDTO> dtos) {
        organizationService.batchAddOrgRelations(dtos);
        return Result.ok();
    }

    /**
     * 批量删除组织关系
     */
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('system:org:remove')")
    public Result<Void> batchRemoveRelations(@RequestBody List<String> ids) {
        organizationService.batchRemoveOrgRelations(ids);
        return Result.ok();
    }
} 