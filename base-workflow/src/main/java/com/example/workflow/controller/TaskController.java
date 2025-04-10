package com.example.workflow.controller;

import com.example.auth.response.Result;
import com.example.workflow.model.vo.TaskVO;
import com.example.workflow.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 任务控制器
 *
 * @author Author
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/workflow/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     * 查询任务列表
     *
     * @param assignee 办理人
     * @param candidateUser 候选人
     * @param candidateGroup 候选组
     * @param processInstanceId 流程实例ID
     * @param processDefinitionKey 流程定义标识
     * @return 任务列表
     */
    @GetMapping("/list")
    public Result<List<TaskVO>> listTasks(
            @RequestParam(value = "assignee", required = false) String assignee,
            @RequestParam(value = "candidateUser", required = false) String candidateUser,
            @RequestParam(value = "candidateGroup", required = false) String candidateGroup,
            @RequestParam(value = "processInstanceId", required = false) String processInstanceId,
            @RequestParam(value = "processDefinitionKey", required = false) String processDefinitionKey) {
        try {
            List<TaskVO> tasks = taskService.listTasks(assignee, candidateUser, candidateGroup, processInstanceId, processDefinitionKey);
            return Result.ok(tasks);
        } catch (Exception e) {
            log.error("查询任务列表失败", e);
            return Result.error("查询任务列表失败"+e);
        }
    }

    /**
     * 获取任务详情
     *
     * @param taskId 任务ID
     * @return 任务详情
     */
    @GetMapping("/{taskId}")
    public Result<TaskVO> getTaskById(@PathVariable("taskId") String taskId) {
        try {
            TaskVO task = taskService.getTaskById(taskId);
            if (task != null) {
                return Result.ok(task);
            } else {
                return  Result.ok(null);
            }
        } catch (Exception e) {
            log.error("获取任务详情失败", e);
            return Result.error("获取任务详情失败"+e);
        }
    }

    /**
     * 完成任务
     *
     * @param taskId 任务ID
     * @param variables 任务变量
     * @return 操作结果
     */
    @PostMapping("/{taskId}/complete")
    public Result<String> completeTask(
            @PathVariable("taskId") String taskId,
            @RequestBody(required = false) Map<String, Object> variables) {
        try {
            taskService.completeTask(taskId, variables);
            return Result.ok("完成任务成功");
        } catch (Exception e) {
            log.error("完成任务失败", e);
            return Result.error("完成任务失败：" + e.getMessage());
        }
    }

    /**
     * 签收任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 操作结果
     */
    @PostMapping("/{taskId}/claim")
    public Result<String> claimTask(
            @PathVariable("taskId") String taskId,
            @RequestParam("userId") String userId) {
        try {
            taskService.claimTask(taskId, userId);
            return Result.ok("签收任务成功");
        } catch (Exception e) {
            log.error("签收任务失败", e);
            return Result.error("签收任务失败：" + e.getMessage());
        }
    }

    /**
     * 取消签收任务
     *
     * @param taskId 任务ID
     * @return 操作结果
     */
    @PostMapping("/{taskId}/unclaim")
    public Result<String> unclaimTask(@PathVariable("taskId") String taskId) {
        try {
            taskService.unclaimTask(taskId);
            return Result.ok("取消签收任务成功");
        } catch (Exception e) {
            log.error("取消签收任务失败", e);
            return Result.error("取消签收任务失败：" + e.getMessage());
        }
    }

    /**
     * 委派任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 操作结果
     */
    @PostMapping("/{taskId}/delegate")
    public Result<String> delegateTask(
            @PathVariable("taskId") String taskId,
            @RequestParam("userId") String userId) {
        try {
            taskService.delegateTask(taskId, userId);
            return Result.ok("委派任务成功");
        } catch (Exception e) {
            log.error("委派任务失败", e);
            return Result.error("委派任务失败：" + e.getMessage());
        }
    }

    /**
     * 转办任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 操作结果
     */
    @PostMapping("/{taskId}/transfer")
    public Result<String> transferTask(
            @PathVariable("taskId") String taskId,
            @RequestParam("userId") String userId) {
        try {
            taskService.transferTask(taskId, userId);
            return Result.ok("转办任务成功");
        } catch (Exception e) {
            log.error("转办任务失败", e);
            return Result.error("转办任务失败：" + e.getMessage());
        }
    }

    /**
     * 设置任务处理人
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 操作结果
     */
    @PostMapping("/{taskId}/assignee")
    public Result<String> setAssignee(
            @PathVariable("taskId") String taskId,
            @RequestParam("userId") String userId) {
        try {
            taskService.setAssignee(taskId, userId);
            return Result.ok("设置任务处理人成功");
        } catch (Exception e) {
            log.error("设置任务处理人失败", e);
            return Result.error("设置任务处理人失败：" + e.getMessage());
        }
    }

    /**
     * 添加候选人
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 操作结果
     */
    @PostMapping("/{taskId}/candidate-user")
    public Result<String> addCandidateUser(
            @PathVariable("taskId") String taskId,
            @RequestParam("userId") String userId) {
        try {
            taskService.addCandidateUser(taskId, userId);
            return Result.ok("添加候选人成功");
        } catch (Exception e) {
            log.error("添加候选人失败", e);
            return Result.error("添加候选人失败：" + e.getMessage());
        }
    }

    /**
     * 删除候选人
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/{taskId}/candidate-user")
    public Result<String> deleteCandidateUser(
            @PathVariable("taskId") String taskId,
            @RequestParam("userId") String userId) {
        try {
            taskService.deleteCandidateUser(taskId, userId);
            return Result.ok("删除候选人成功");
        } catch (Exception e) {
            log.error("删除候选人失败", e);
            return Result.error("删除候选人失败：" + e.getMessage());
        }
    }

    /**
     * 添加候选组
     *
     * @param taskId 任务ID
     * @param groupId 组ID
     * @return 操作结果
     */
    @PostMapping("/{taskId}/candidate-group")
    public Result<String> addCandidateGroup(
            @PathVariable("taskId") String taskId,
            @RequestParam("groupId") String groupId) {
        try {
            taskService.addCandidateGroup(taskId, groupId);
            return Result.ok("添加候选组成功");
        } catch (Exception e) {
            log.error("添加候选组失败", e);
            return Result.error("添加候选组失败：" + e.getMessage());
        }
    }

    /**
     * 删除候选组
     *
     * @param taskId 任务ID
     * @param groupId 组ID
     * @return 操作结果
     */
    @DeleteMapping("/{taskId}/candidate-group")
    public Result<String> deleteCandidateGroup(
            @PathVariable("taskId") String taskId,
            @RequestParam("groupId") String groupId) {
        try {
            taskService.deleteCandidateGroup(taskId, groupId);
            return Result.ok("删除候选组成功");
        } catch (Exception e) {
            log.error("删除候选组失败", e);
            return Result.error("删除候选组失败：" + e.getMessage());
        }
    }

    /**
     * 获取任务变量
     *
     * @param taskId 任务ID
     * @return 任务变量
     */
    @GetMapping("/{taskId}/variables")
    public Result<Map<String, Object>> getTaskVariables(@PathVariable("taskId") String taskId) {
        try {
            Map<String, Object> variables = taskService.getTaskVariables(taskId);
            return Result.ok(variables);
        } catch (Exception e) {
            log.error("获取任务变量失败", e);
            return Result.error("获取任务表单失败"+e);
        }
    }

    /**
     * 设置任务变量
     *
     * @param taskId 任务ID
     * @param variables 任务变量
     * @return 操作结果
     */
    @PostMapping("/{taskId}/variables")
    public Result<String> setTaskVariables(
            @PathVariable("taskId") String taskId,
            @RequestBody Map<String, Object> variables) {
        try {
            taskService.setTaskVariables(taskId, variables);
            return Result.ok("设置任务变量成功");
        } catch (Exception e) {
            log.error("设置任务变量失败", e);
            return Result.error("设置任务变量失败：" + e.getMessage());
        }
    }

    /**
     * 获取任务表单
     *
     * @param taskId 任务ID
     * @return 任务表单
     */
    @GetMapping("/{taskId}/form")
    public Result<String> getTaskForm(@PathVariable("taskId") String taskId) {
        try {
            String formKey = taskService.getTaskFormKey(taskId);
            return Result.ok(formKey);
        } catch (Exception e) {
            log.error("获取任务表单失败", e);
            return Result.error("获取任务表单失败"+e);
        }
    }
} 