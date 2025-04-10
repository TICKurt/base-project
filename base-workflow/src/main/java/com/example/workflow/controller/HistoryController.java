package com.example.workflow.controller;

import com.example.auth.response.Result;
import com.example.workflow.model.vo.HistoricProcessInstanceVO;
import com.example.workflow.model.vo.HistoricTaskInstanceVO;
import com.example.workflow.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 历史控制器
 *
 * @author Author
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/workflow/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    /**
     * 查询历史流程实例列表
     *
     * @param processDefinitionKey 流程定义标识
     * @param businessKey 业务标识
     * @param startUserId 发起人ID
     * @param finished 是否已完成
     * @return 历史流程实例列表
     */
    @GetMapping("/process-instance/list")
    public Result<List<HistoricProcessInstanceVO>> listHistoricProcessInstances(
            @RequestParam(value = "processDefinitionKey", required = false) String processDefinitionKey,
            @RequestParam(value = "businessKey", required = false) String businessKey,
            @RequestParam(value = "startUserId", required = false) String startUserId,
            @RequestParam(value = "finished", required = false) Boolean finished) {
        try {
            // 调用historyService的listHistoricProcessInstances方法，查询历史流程实例列表
            List<HistoricProcessInstanceVO> list = historyService.listHistoricProcessInstances(processDefinitionKey, businessKey, startUserId, finished);
            // 返回查询结果
            return Result.ok(list);
        } catch (Exception e) {
            // 捕获异常，记录错误日志，并返回500错误
            log.error("查询历史流程实例列表失败", e);
            return Result.error("查询历史流程实例列表失败"+e);
        }
    }

    /**
     * 获取历史流程实例详情
     *
     * @param processInstanceId 流程实例ID
     * @return 历史流程实例详情
     */
    @GetMapping("/process-instance/{processInstanceId}")
    public Result<HistoricProcessInstanceVO> getHistoricProcessInstanceById(@PathVariable("processInstanceId") String processInstanceId) {
        try {
            HistoricProcessInstanceVO historicProcessInstance = historyService.getHistoricProcessInstanceById(processInstanceId);
            if (historicProcessInstance != null) {
                return Result.ok(historicProcessInstance);
            } else {
                return  Result.ok(null);
            }
        } catch (Exception e) {
            log.error("获取历史流程实例详情失败", e);
            return Result.error("获取历史流程实例详情失败"+e);
        }
    }

    /**
     * 查询历史任务列表
     *
     * @param processInstanceId 流程实例ID
     * @param taskName 任务名称
     * @param assignee 办理人
     * @param finished 是否已完成
     * @return 历史任务列表
     */
    @GetMapping("/task/list")
    public Result<List<HistoricTaskInstanceVO>> listHistoricTasks(
            @RequestParam(value = "processInstanceId", required = false) String processInstanceId,
            @RequestParam(value = "taskName", required = false) String taskName,
            @RequestParam(value = "assignee", required = false) String assignee,
            @RequestParam(value = "finished", required = false) Boolean finished) {
        try {
            List<HistoricTaskInstanceVO> list = historyService.listHistoricTasks(processInstanceId, taskName, assignee, finished);
            return Result.ok(list);
        } catch (Exception e) {
            log.error("查询历史任务列表失败", e);
            return Result.error("查询历史任务列表失败"+e);
        }
    }

    /**
     * 获取历史任务详情
     *
     * @param taskId 任务ID
     * @return 历史任务详情
     */
    @GetMapping("/task/{taskId}")
    public Result<HistoricTaskInstanceVO> getHistoricTaskById(@PathVariable("taskId") String taskId) {
        try {
            HistoricTaskInstanceVO historicTask = historyService.getHistoricTaskById(taskId);
            if (historicTask != null) {
                return Result.ok(historicTask);
            } else {
                return  Result.ok(null);
            }
        } catch (Exception e) {
            log.error("获取历史任务详情失败", e);
            return Result.error("获取历史任务详情失败"+e);
        }
    }

    /**
     * 获取历史流程变量
     *
     * @param processInstanceId 流程实例ID
     * @return 历史流程变量
     */
    @GetMapping("/process-instance/{processInstanceId}/variables")
    public Result<Map<String, Object>> getHistoricProcessVariables(@PathVariable("processInstanceId") String processInstanceId) {
        try {
            Map<String, Object> variables = historyService.getHistoricProcessVariables(processInstanceId);
            return Result.ok(variables);
        } catch (Exception e) {
            log.error("获取历史流程变量失败", e);
            return Result.error("获取历史流程变量失败"+e);
        }
    }

    /**
     * 获取历史任务表单数据
     *
     * @param taskId 任务ID
     * @return 历史任务表单数据
     */
    @GetMapping("/task/{taskId}/form-data")
    public Result<Map<String, Object>> getHistoricTaskFormData(@PathVariable("taskId") String taskId) {
        try {
            Map<String, Object> formData = historyService.getHistoricTaskFormData(taskId);
            return Result.ok(formData);
        } catch (Exception e) {
            log.error("获取历史任务表单数据失败", e);
            return Result.error("获取历史任务表单数据失败"+e);
        }
    }

    /**
     * 删除历史流程实例
     *
     * @param processInstanceId 流程实例ID
     * @return 操作结果
     */
    @DeleteMapping("/process-instance/{processInstanceId}")
    public Result<String> deleteHistoricProcessInstance(@PathVariable("processInstanceId") String processInstanceId) {
        try {
            historyService.deleteHistoricProcessInstance(processInstanceId);
            return Result.ok("删除成功");
        } catch (Exception e) {
            log.error("删除历史流程实例失败", e);
            return Result.error("删除历史流程实例失败：" + e.getMessage());
        }
    }
} 