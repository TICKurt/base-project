package com.example.workflow.controller;

import com.example.common.model.FileInfo;
import com.example.auth.response.Result;
import com.example.workflow.model.dto.BpmnDeployDTO;
import com.example.workflow.model.vo.ProcessDefinitionVO;
import com.example.workflow.service.ProcessDefinitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 流程定义控制器
 *
 * @author Author
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/workflow/process-definition")
@RequiredArgsConstructor
public class ProcessDefinitionController {

    private final ProcessDefinitionService processDefinitionService;

    /**
     * 部署流程定义
     *
     * @param name     流程定义名称
     * @param category 流程定义分类
     * @param file     流程定义文件
     * @return 部署ID
     */
    @PostMapping("/deploy")
    public Result<String> deploy(
            @RequestParam("name") String name,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam("file") MultipartFile file) {
        try {
            String deploymentId = processDefinitionService.deploy(name, category, file);
            return Result.ok(deploymentId);
        } catch (Exception e) {
            log.error("部署流程定义失败", e);
            return Result.error("部署流程定义失败：" + e.getMessage());
        }
    }

    /**
     * 通过JSON方式部署流程定义
     *
     * @param deployDTO 部署信息DTO
     * @return 部署ID
     */
    @PostMapping("/deploy-json")
    public Result<String> deployByJson(@RequestBody BpmnDeployDTO deployDTO) {
        try {
            String deploymentId = processDefinitionService.deployByBpmnXml(deployDTO);
            return Result.ok(deploymentId);
        } catch (IllegalArgumentException e) {
            log.error("部署流程定义参数错误", e);
            return Result.error("部署流程定义失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("部署流程定义失败", e);
            return Result.error("部署流程定义失败：" + e.getMessage());
        }
    }

    /**
     * 删除流程定义
     *
     * @param deploymentId 部署ID
     * @param cascade      是否级联删除
     * @return 操作结果
     */
    @DeleteMapping("/deployment/{deploymentId}")
    public Result<String> deleteDeployment(
            @PathVariable("deploymentId") String deploymentId,
            @RequestParam(value = "cascade", defaultValue = "false") boolean cascade) {
        try {
            processDefinitionService.deleteByDeploymentId(deploymentId, cascade);
            return Result.ok("删除成功");
        } catch (Exception e) {
            log.error("删除流程定义失败", e);
            return Result.error("删除流程定义失败：" + e.getMessage());
        }
    }

    /**
     * 获取流程定义列表
     *
     * @param category 流程定义分类
     * @param key      流程定义标识
     * @param name     流程定义名称
     * @param tenantId 租户ID
     * @return 流程定义列表
     */
    @GetMapping("/list")
    public Result<List<ProcessDefinitionVO>> list(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "tenantId", required = false) String tenantId) {
        try {
            List<ProcessDefinitionVO> list = processDefinitionService.list(category, key, name, tenantId);
            return Result.ok(list);
        } catch (Exception e) {
            log.error("获取流程定义列表失败", e);
            return Result.error(null);
        }
    }

    /**
     * 获取流程定义详情
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程定义详情
     */
    @GetMapping("/{processDefinitionId}")
    public Result<ProcessDefinitionVO> getById(@PathVariable("processDefinitionId") String processDefinitionId) {
        try {
            ProcessDefinitionVO processDefinition = processDefinitionService.getById(processDefinitionId);
            if (processDefinition != null) {
                return Result.ok(processDefinition);
            } else {
                return Result.ok(null);
            }
        } catch (Exception e) {
            log.error("获取流程定义详情失败", e);
            return Result.error("获取流程定义详情失败" + e.getMessage());
        }
    }

    /**
     * 获取最新版本的流程定义
     *
     * @param processDefinitionKey 流程定义标识
     * @return 流程定义详情
     */
    @GetMapping("/latest/{processDefinitionKey}")
    public Result<ProcessDefinitionVO> getLatestByKey(@PathVariable("processDefinitionKey") String processDefinitionKey) {
        try {
            ProcessDefinitionVO processDefinition = processDefinitionService.getLatestByKey(processDefinitionKey);
            if (processDefinition != null) {
                return Result.ok(processDefinition);
            } else {
                return Result.ok(null);
            }
        } catch (Exception e) {
            log.error("获取最新版本流程定义失败", e);
            return Result.error("获取最新版本流程定义失败" + e.getMessage());
        }
    }

    /**
     * 获取流程定义XML
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程定义XML
     */
    @GetMapping("/{processDefinitionId}/xml")
    public Result<String> getProcessDefinitionXML(@PathVariable("processDefinitionId") String processDefinitionId) {
        try {
            String xml = processDefinitionService.getProcessDefinitionXML(processDefinitionId);
            if (xml != null) {
                return Result.ok(xml);
            } else {
                return Result.ok(null);
            }
        } catch (Exception e) {
            log.error("获取流程定义XML失败", e);
            return Result.error("获取流程定义XML失败" + e.getMessage());
        }
    }

    /**
     * 获取流程图
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程图文件信息
     */
    @GetMapping("/{processDefinitionId}/diagram")
    public Result<FileInfo> getProcessDiagram(@PathVariable("processDefinitionId") String processDefinitionId) {
        log.info("请求获取流程图, processDefinitionId: {}", processDefinitionId);
        try {
            if (StringUtils.isBlank(processDefinitionId)) {
                log.warn("获取流程图失败: processDefinitionId为空");
                return Result.error("流程定义ID不能为空");
            }
            
            FileInfo fileInfo = processDefinitionService.getProcessDiagram(processDefinitionId);
            if (fileInfo != null) {
                log.info("成功获取流程图: {}", fileInfo.getPath());
                return Result.ok(fileInfo);
            } else {
                log.warn("未找到流程图: processDefinitionId={}", processDefinitionId);
                return Result.error("未找到流程图，可能原因：1.流程定义不存在；2.流程定义没有关联的流程图资源");
            }
        } catch (Exception e) {
            log.error("获取流程图失败, processDefinitionId: {}, 异常: {}", processDefinitionId, e.getMessage(), e);
            return Result.error("获取流程图失败: " + e.getMessage());
        }
    }

    /**
     * 获取流程资源
     *
     * @param deploymentId  部署ID
     * @param resourceName  资源名称
     * @return 流程资源
     */
    @GetMapping("/deployment/{deploymentId}/resource/{resourceName}")
    public Result<byte[]> getResource(
            @PathVariable("deploymentId") String deploymentId,
            @PathVariable("resourceName") String resourceName) {
        try {
            byte[] bytes = processDefinitionService.getResource(deploymentId, resourceName);
            if (bytes != null && bytes.length > 0) {
                HttpHeaders headers = new HttpHeaders();
                if (resourceName.endsWith(".png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (resourceName.endsWith(".xml") || resourceName.endsWith(".bpmn") || resourceName.endsWith(".bpmn20.xml")) {
                    headers.setContentType(MediaType.TEXT_XML);
                } else {
                    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                }
                return Result.ok(bytes);
            } else {
                return Result.ok();
            }
        } catch (Exception e) {
            log.error("获取流程资源失败", e);
            return Result.error(null);
        }
    }

    /**
     * 验证BPMN文件
     *
     * @param file BPMN文件
     * @return 验证结果
     */
    @PostMapping("/validate")
    public Result<Boolean> validateBpmnFile(@RequestParam("file") MultipartFile file) {
        try {
            boolean valid = processDefinitionService.validateBpmnFile(file);
            return Result.ok(valid);
        } catch (Exception e) {
            log.error("验证BPMN文件失败", e);
            return Result.error("验证BPMN文件失败"+e.getMessage());
        }
    }

    /**
     * 验证BPMN XML内容
     *
     * @param bpmnXml BPMN XML内容
     * @return 验证结果
     */
    @PostMapping("/validate-xml")
    public Result<Boolean> validateBpmnXml(@RequestBody String bpmnXml) {
        try {
            // 调用processDefinitionService的validateBpmnXml方法验证BPMN XML内容
            boolean valid = processDefinitionService.validateBpmnXml(bpmnXml);
            // 返回验证结果
            return Result.ok(valid);
        } catch (Exception e) {
            // 记录错误日志
            log.error("验证BPMN XML内容失败", e);
            // 返回500错误码和false
            return Result.error("验证BPMN XML内容失败");
        }
    }

    /**
     * 激活流程定义
     *
     * @param processDefinitionId 流程定义ID
     * @return 操作结果
     */
    @PutMapping("/{processDefinitionId}/activate")
    public Result<String> activate(@PathVariable("processDefinitionId") String processDefinitionId) {
        try {
            processDefinitionService.activate(processDefinitionId);
            return Result.ok("激活成功");
        } catch (Exception e) {
            log.error("激活流程定义失败", e);
            return Result.error("激活流程定义失败：" + e.getMessage());
        }
    }

    /**
     * 挂起流程定义
     *
     * @param processDefinitionId 流程定义ID
     * @return 操作结果
     */
    @PutMapping("/{processDefinitionId}/suspend")
    public Result<String> suspend(@PathVariable("processDefinitionId") String processDefinitionId) {
        try {
            processDefinitionService.suspend(processDefinitionId);
            return Result.ok("挂起成功");
        } catch (Exception e) {
            log.error("挂起流程定义失败", e);
            return Result.error("挂起流程定义失败：" + e.getMessage());
        }
    }
} 