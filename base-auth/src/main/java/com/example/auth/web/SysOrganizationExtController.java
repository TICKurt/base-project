package com.example.auth.web;

import com.example.auth.dto.SysOrganizationDTO;
import com.example.auth.entity.SysOrganizationExt;
import com.example.auth.service.SysOrganizationService;
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
    public ResponseEntity<Map<String, Object>> getInfo(@PathVariable String orgId) {
        Map<String, Object> ext = organizationService.getOrgExtById(orgId);
        return ResponseEntity.ok(ext);
    }

    /**
     * 保存组织机构扩展信息
     */
    @PostMapping
    @PreAuthorize("hasAuthority('system:org:edit')")
    public ResponseEntity<Void> saveExt(@Valid @RequestBody SysOrganizationDTO dto) {
        organizationService.saveOrgExt(dto);
        return ResponseEntity.ok().build();
    }

    /**
     * 更新组织机构扩展信息
     */
    @PutMapping
    @PreAuthorize("hasAuthority('system:org:edit')")
    public ResponseEntity<Void> updateExt(@Valid @RequestBody SysOrganizationDTO dto) {
        organizationService.updateOrgExt(dto);
        return ResponseEntity.ok().build();
    }

    /**
     * 删除组织机构扩展信息
     */
    @DeleteMapping("/{orgId}")
    @PreAuthorize("hasAuthority('system:org:remove')")
    public ResponseEntity<Void> removeExt(@PathVariable String orgId) {
        organizationService.removeOrgExt(orgId);
        return ResponseEntity.ok().build();
    }

    /**
     * 上传组织机构Logo
     */
    @PostMapping("/uploadLogo/{orgId}")
    @PreAuthorize("hasAuthority('system:org:edit')")
    public ResponseEntity<String> uploadLogo(@PathVariable String orgId, @RequestParam("file") Object file) {
        String logoUrl = organizationService.uploadOrgLogo(orgId, file);
        return ResponseEntity.ok(logoUrl);
    }

    /**
     * 上传组织机构营业执照
     */
    @PostMapping("/uploadLicense/{orgId}")
    @PreAuthorize("hasAuthority('system:org:edit')")
    public ResponseEntity<String> uploadLicense(@PathVariable String orgId, @RequestParam("file") Object file) {
        String licenseUrl = organizationService.uploadOrgLicense(orgId, file);
        return ResponseEntity.ok(licenseUrl);
    }
} 