package com.example.workflow.controller;

import com.example.core.response.Result;
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
    public Result<String> startProcessInstance(
            @RequestParam("processDefinitionId") String processDefinitionId,
            @RequestParam(value = "businessKey", required = false) String businessKey,
            @RequestBody(required = false) Map<String, Object> variables) {
        try {
            String processInstanceId = processInstanceService.startProcessInstance(processDefinitionId, businessKey, variables);
            return Result.ok(processInstanceId);
        } catch (Exception e) {
            log.error("启动流程实例失败", e);
            return Result.fail("启动流程实例失败：" + e.getMessage());
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
    public Result<String> startProcessInstanceByKey(
            @RequestParam("processDefinitionKey") String processDefinitionKey,
            @RequestParam(value = "businessKey", required = false) String businessKey,
            @RequestBody(required = false) Map<String, Object> variables) {
        try {
            String processInstanceId = processInstanceService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
            return Result.ok(processInstanceId);
        } catch (Exception e) {
            log.error("启动流程实例失败", e);
            return Result.fail("启动流程实例失败：" + e.getMessage());
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
    public Result<List<ProcessInstanceVO>> listProcessInstances(
            @RequestParam(value = "processDefinitionKey", required = false) String processDefinitionKey,
            @RequestParam(value = "businessKey", required = false) String businessKey,
            @RequestParam(value = "startUserId", required = false) String startUserId,
            @RequestParam(value = "active", required = false) Boolean active,
            @RequestParam(value = "suspended", required = false) Boolean suspended) {
        try {
            List<ProcessInstanceVO> list = processInstanceService.listProcessInstances(processDefinitionKey, businessKey, startUserId, active, suspended);
            return Result.ok(list);
        } catch (Exception e) {
            log.error("查询流程实例列表失败", e);
            return Result.fail(null);
        }
    }

    /**
     * 获取流程实例详情
     *
     * @param processInstanceId 流程实例ID
     * @return 流程实例详情
     */
    @GetMapping("/{processInstanceId}")
    public Result<ProcessInstanceVO> getProcessInstanceById(@PathVariable("processInstanceId") String processInstanceId) {
        try {
            ProcessInstanceVO processInstance = processInstanceService.getProcessInstanceById(processInstanceId);
            if (processInstance != null) {
                return Result.ok(processInstance);
            } else {
                return  Result.ok(null);
            }
        } catch (Exception e) {
            log.error("获取流程实例详情失败", e);
            return Result.fail(null);
        }
    }

    /**
     * 挂起流程实例
     *
     * @param processInstanceId 流程实例ID
     * @return 操作结果
     */
    @PutMapping("/{processInstanceId}/suspend")
    public Result<String> suspendProcessInstance(@PathVariable("processInstanceId") String processInstanceId) {
        try {
            processInstanceService.suspendProcessInstance(processInstanceId);
            return Result.ok("挂起成功");
        } catch (Exception e) {
            log.error("挂起流程实例失败", e);
            return Result.fail("挂起流程实例失败：" + e.getMessage());
        }
    }

    /**
     * 激活流程实例
     *
     * @param processInstanceId 流程实例ID
     * @return 操作结果
     */
    @PutMapping("/{processInstanceId}/activate")
    public Result<String> activateProcessInstance(@PathVariable("processInstanceId") String processInstanceId) {
        try {
            processInstanceService.activateProcessInstance(processInstanceId);
            return Result.ok("激活成功");
        } catch (Exception e) {
            log.error("激活流程实例失败", e);
            return Result.fail("激活流程实例失败：" + e.getMessage());
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
    public Result<String> deleteProcessInstance(
            @PathVariable("processInstanceId") String processInstanceId,
            @RequestParam(value = "deleteReason", required = false) String deleteReason) {
        try {
            processInstanceService.deleteProcessInstance(processInstanceId, deleteReason);
            return Result.ok("删除成功");
        } catch (Exception e) {
            log.error("删除流程实例失败", e);
            return Result.fail("删除流程实例失败：" + e.getMessage());
        }
    }

    /**
     * 获取流程变量
     *
     * @param processInstanceId 流程实例ID
     * @return 流程变量
     */
    @GetMapping("/{processInstanceId}/variables")
    public Result<Map<String, Object>> getProcessInstanceVariables(@PathVariable("processInstanceId") String processInstanceId) {
        try {
            Map<String, Object> variables = processInstanceService.getProcessInstanceVariables(processInstanceId);
            return Result.ok(variables);
        } catch (Exception e) {
            log.error("获取流程变量失败", e);
            return Result.fail(null);
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
    public Result<String> setProcessInstanceVariables(
            @PathVariable("processInstanceId") String processInstanceId,
            @RequestBody Map<String, Object> variables) {
        try {
            processInstanceService.setProcessInstanceVariables(processInstanceId, variables);
            return Result.ok("设置成功");
        } catch (Exception e) {
            log.error("设置流程变量失败", e);
            return Result.fail("设置流程变量失败：" + e.getMessage());
        }
    }
} 