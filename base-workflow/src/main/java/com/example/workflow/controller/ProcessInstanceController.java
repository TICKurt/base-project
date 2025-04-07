package com.example.workflow.controller;

import com.example.workflow.model.vo.ProcessInstanceVO;
import com.example.workflow.service.ProcessInstanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 流程实例控制器
 *
 * @author Author
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/workflow/process-instance")
@RequiredArgsConstructor
public class ProcessInstanceController {

    private final ProcessInstanceService processInstanceService;

    /**
     * 启动流程实例
     *
     * @param processDefinitionId 流程定义ID
     * @param businessKey 业务标识
     * @param variables 流程变量
     * @return 流程实例ID
     */
    @PostMapping("/start")
    public ResponseEntity<String> startProcessInstance(
            @RequestParam("processDefinitionId") String processDefinitionId,
            @RequestParam(value = "businessKey", required = false) String businessKey,
            @RequestBody(required = false) Map<String, Object> variables) {
        try {
            String processInstanceId = processInstanceService.startProcessInstance(processDefinitionId, businessKey, variables);
            return ResponseEntity.ok(processInstanceId);
        } catch (Exception e) {
            log.error("启动流程实例失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("启动流程实例失败：" + e.getMessage());
        }
    }

    /**
     * 启动流程实例（通过流程定义标识）
     *
     * @param processDefinitionKey 流程定义标识
     * @param businessKey 业务标识
     * @param variables 流程变量
     * @return 流程实例ID
     */
    @PostMapping("/start-by-key")
    public ResponseEntity<String> startProcessInstanceByKey(
            @RequestParam("processDefinitionKey") String processDefinitionKey,
            @RequestParam(value = "businessKey", required = false) String businessKey,
            @RequestBody(required = false) Map<String, Object> variables) {
        try {
            String processInstanceId = processInstanceService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
            return ResponseEntity.ok(processInstanceId);
        } catch (Exception e) {
            log.error("启动流程实例失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("启动流程实例失败：" + e.getMessage());
        }
    }

    /**
     * 查询流程实例列表
     *
     * @param processDefinitionKey 流程定义标识
     * @param businessKey 业务标识
     * @param startUserId 发起人ID
     * @param active 是否活动
     * @param suspended 是否挂起
     * @return 流程实例列表
     */
    @GetMapping("/list")
    public ResponseEntity<List<ProcessInstanceVO>> listProcessInstances(
            @RequestParam(value = "processDefinitionKey", required = false) String processDefinitionKey,
            @RequestParam(value = "businessKey", required = false) String businessKey,
            @RequestParam(value = "startUserId", required = false) String startUserId,
            @RequestParam(value = "active", required = false) Boolean active,
            @RequestParam(value = "suspended", required = false) Boolean suspended) {
        try {
            List<ProcessInstanceVO> list = processInstanceService.listProcessInstances(processDefinitionKey, businessKey, startUserId, active, suspended);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("查询流程实例列表失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 获取流程实例详情
     *
     * @param processInstanceId 流程实例ID
     * @return 流程实例详情
     */
    @GetMapping("/{processInstanceId}")
    public ResponseEntity<ProcessInstanceVO> getProcessInstanceById(@PathVariable("processInstanceId") String processInstanceId) {
        try {
            ProcessInstanceVO processInstance = processInstanceService.getProcessInstanceById(processInstanceId);
            if (processInstance != null) {
                return ResponseEntity.ok(processInstance);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("获取流程实例详情失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 挂起流程实例
     *
     * @param processInstanceId 流程实例ID
     * @return 操作结果
     */
    @PutMapping("/{processInstanceId}/suspend")
    public ResponseEntity<String> suspendProcessInstance(@PathVariable("processInstanceId") String processInstanceId) {
        try {
            processInstanceService.suspendProcessInstance(processInstanceId);
            return ResponseEntity.ok("挂起成功");
        } catch (Exception e) {
            log.error("挂起流程实例失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("挂起流程实例失败：" + e.getMessage());
        }
    }

    /**
     * 激活流程实例
     *
     * @param processInstanceId 流程实例ID
     * @return 操作结果
     */
    @PutMapping("/{processInstanceId}/activate")
    public ResponseEntity<String> activateProcessInstance(@PathVariable("processInstanceId") String processInstanceId) {
        try {
            processInstanceService.activateProcessInstance(processInstanceId);
            return ResponseEntity.ok("激活成功");
        } catch (Exception e) {
            log.error("激活流程实例失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("激活流程实例失败：" + e.getMessage());
        }
    }

    /**
     * 删除流程实例
     *
     * @param processInstanceId 流程实例ID
     * @param deleteReason 删除原因
     * @return 操作结果
     */
    @DeleteMapping("/{processInstanceId}")
    public ResponseEntity<String> deleteProcessInstance(
            @PathVariable("processInstanceId") String processInstanceId,
            @RequestParam(value = "deleteReason", required = false) String deleteReason) {
        try {
            processInstanceService.deleteProcessInstance(processInstanceId, deleteReason);
            return ResponseEntity.ok("删除成功");
        } catch (Exception e) {
            log.error("删除流程实例失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除流程实例失败：" + e.getMessage());
        }
    }

    /**
     * 获取流程变量
     *
     * @param processInstanceId 流程实例ID
     * @return 流程变量
     */
    @GetMapping("/{processInstanceId}/variables")
    public ResponseEntity<Map<String, Object>> getProcessInstanceVariables(@PathVariable("processInstanceId") String processInstanceId) {
        try {
            Map<String, Object> variables = processInstanceService.getProcessInstanceVariables(processInstanceId);
            return ResponseEntity.ok(variables);
        } catch (Exception e) {
            log.error("获取流程变量失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 设置流程变量
     *
     * @param processInstanceId 流程实例ID
     * @param variables 流程变量
     * @return 操作结果
     */
    @PostMapping("/{processInstanceId}/variables")
    public ResponseEntity<String> setProcessInstanceVariables(
            @PathVariable("processInstanceId") String processInstanceId,
            @RequestBody Map<String, Object> variables) {
        try {
            processInstanceService.setProcessInstanceVariables(processInstanceId, variables);
            return ResponseEntity.ok("设置成功");
        } catch (Exception e) {
            log.error("设置流程变量失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("设置流程变量失败：" + e.getMessage());
        }
    }
} 