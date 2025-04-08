package com.example.auth.web;

import com.example.auth.dto.SysOrganizationDTO;
import com.example.auth.entity.SysOrganizationExt;
import com.example.auth.service.SysOrganizationService;
import com.example.core.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 组织机构扩展信息控制器
 *
 * @author 作者
 * @date 创建时间
 */
@RestController
@RequestMapping("/organization/ext")
public class SysOrganizationExtController {

    @Autowired
    private SysOrganizationService organizationService;

    /**
     * 获取组织机构扩展信息
     */
    @GetMapping("/{orgId}")
    @PreAuthorize("hasAuthority('system:org:query')")
    public Result<Map<String, Object>> getInfo(@PathVariable String orgId) {
        Map<String, Object> ext = organizationService.getOrgExtById(orgId);
        return Result.ok(ext);
    }

    /**
     * 保存组织机构扩展信息
     */
    @PostMapping
    @PreAuthorize("hasAuthority('system:org:edit')")
    public Result<Void> saveExt(@Valid @RequestBody SysOrganizationDTO dto) {
        // 调用organizationService的saveOrgExt方法，保存组织机构扩展信息
        organizationService.saveOrgExt(dto);
        // 返回一个成功的Result对象
        return Result.ok();
    }

    /**
     * 更新组织机构扩展信息
     */
    @PutMapping
    @PreAuthorize("hasAuthority('system:org:edit')")
    public Result<Void> updateExt(@Valid @RequestBody SysOrganizationDTO dto) {
        organizationService.updateOrgExt(dto);
        return Result.ok();
    }

    /**
     * 删除组织机构扩展信息
     */
    @DeleteMapping("/{orgId}")
    @PreAuthorize("hasAuthority('system:org:remove')")
    public Result<Void> removeExt(@PathVariable String orgId) {
        organizationService.removeOrgExt(orgId);
        return Result.ok();
    }

    /**
     * 上传组织机构Logo
     */
    @PostMapping("/uploadLogo/{orgId}")
    @PreAuthorize("hasAuthority('system:org:edit')")
    public Result<String> uploadLogo(@PathVariable String orgId, @RequestParam("file") Object file) {
        String logoUrl = organizationService.uploadOrgLogo(orgId, file);
        return Result.ok(logoUrl);
    }

    /**
     * 上传组织机构营业执照
     */
    @PostMapping("/uploadLicense/{orgId}")
    @PreAuthorize("hasAuthority('system:org:edit')")
    public Result<String> uploadLicense(@PathVariable String orgId, @RequestParam("file") Object file) {
        String licenseUrl = organizationService.uploadOrgLicense(orgId, file);
        return Result.ok(licenseUrl);
    }
} 