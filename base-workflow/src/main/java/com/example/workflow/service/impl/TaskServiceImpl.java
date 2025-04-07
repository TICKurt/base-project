package com.example.workflow.service.impl;

import com.example.workflow.model.query.TaskQuery;
import com.example.workflow.model.vo.CommentVO;
import com.example.workflow.model.vo.TaskVO;
import com.example.workflow.service.TaskService;
import com.example.workflow.utils.FlowableUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 任务服务实现类
 *
 * @author Author
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final org.flowable.engine.TaskService flowableTaskService;

    @Override
    public List<TaskVO> listTasks(String assignee, String candidateUser, String candidateGroup, String processInstanceId, String processDefinitionKey) {
        log.debug("查询任务列表: assignee={}, candidateUser={}, candidateGroup={}, processInstanceId={}, processDefinitionKey={}",
                assignee, candidateUser, candidateGroup, processInstanceId, processDefinitionKey);
        
        // 创建查询对象
        org.flowable.task.api.TaskQuery taskQuery = flowableTaskService.createTaskQuery();

        // 设置查询条件
        if (StringUtils.isNotBlank(assignee)) {
            taskQuery.taskAssignee(assignee);
        }
        if (StringUtils.isNotBlank(candidateUser)) {
            taskQuery.taskCandidateUser(candidateUser);
        }
        if (StringUtils.isNotBlank(candidateGroup)) {
            taskQuery.taskCandidateGroup(candidateGroup);
        }
        if (StringUtils.isNotBlank(processInstanceId)) {
            taskQuery.processInstanceId(processInstanceId);
        }
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            taskQuery.processDefinitionKey(processDefinitionKey);
        }

        // 设置排序
        taskQuery.orderByTaskCreateTime().desc();

        // 查询结果
        List<Task> tasks = taskQuery.list();

        // 转换为VO对象
        List<TaskVO> result = new ArrayList<>();
        for (Task task : tasks) {
            result.add(FlowableUtils.toTaskVO(task));
        }

        return result;
    }
    
    /**
     * 使用TaskQuery对象查询任务列表（扩展方法）
     */
    public List<TaskVO> listTasksByQuery(TaskQuery query) {
        log.debug("使用TaskQuery查询任务列表: {}", query);
        
        // 创建查询对象
        org.flowable.task.api.TaskQuery taskQuery = flowableTaskService.createTaskQuery();

        // 设置查询条件
        if (StringUtils.isNotBlank(query.getProcessDefinitionId())) {
            taskQuery.processDefinitionId(query.getProcessDefinitionId());
        }
        if (StringUtils.isNotBlank(query.getProcessDefinitionKey())) {
            taskQuery.processDefinitionKey(query.getProcessDefinitionKey());
        }
        if (StringUtils.isNotBlank(query.getProcessInstanceId())) {
            taskQuery.processInstanceId(query.getProcessInstanceId());
        }
        if (StringUtils.isNotBlank(query.getAssignee())) {
            taskQuery.taskAssignee(query.getAssignee());
        }
        if (StringUtils.isNotBlank(query.getTaskName())) {
            taskQuery.taskNameLike("%" + query.getTaskName() + "%");
        }
        if (StringUtils.isNotBlank(query.getCandidateUser())) {
            taskQuery.taskCandidateUser(query.getCandidateUser());
        }
        if (StringUtils.isNotBlank(query.getCandidateGroup())) {
            taskQuery.taskCandidateGroup(query.getCandidateGroup());
        }
        if (StringUtils.isNotBlank(query.getTenantId())) {
            taskQuery.taskTenantId(query.getTenantId());
        }

        // 设置排序
        taskQuery.orderByTaskCreateTime().desc();

        // 查询结果
        List<Task> tasks = taskQuery.list();

        // 转换为VO对象
        List<TaskVO> result = new ArrayList<>();
        for (Task task : tasks) {
            result.add(FlowableUtils.toTaskVO(task));
        }

        return result;
    }

    @Override
    public TaskVO getTaskById(String taskId) {
        log.debug("获取任务详情: taskId={}", taskId);
        
        // 查询任务
        Task task = flowableTaskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();

        // 转换为VO对象
        return task != null ? FlowableUtils.toTaskVO(task) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeTask(String taskId, Map<String, Object> variables) {
        log.debug("完成任务: taskId={}, variables={}", taskId, variables);
        
        try {
            // 完成任务并设置变量
            if (variables != null && !variables.isEmpty()) {
                flowableTaskService.complete(taskId, variables);
            } else {
                flowableTaskService.complete(taskId);
            }
        } catch (Exception e) {
            log.error("完成任务失败", e);
            throw new RuntimeException("完成任务失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void claimTask(String taskId, String userId) {
        log.debug("认领任务: taskId={}, userId={}", taskId, userId);
        
        try {
            // 认领任务
            flowableTaskService.claim(taskId, userId);
        } catch (Exception e) {
            log.error("认领任务失败", e);
            throw new RuntimeException("认领任务失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unclaimTask(String taskId) {
        log.debug("取消认领任务: taskId={}", taskId);
        
        try {
            // 取消认领任务
            flowableTaskService.unclaim(taskId);
        } catch (Exception e) {
            log.error("取消认领任务失败", e);
            throw new RuntimeException("取消认领任务失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delegateTask(String taskId, String userId) {
        log.debug("委派任务: taskId={}, userId={}", taskId, userId);
        
        try {
            // 委派任务
            flowableTaskService.delegateTask(taskId, userId);
        } catch (Exception e) {
            log.error("委派任务失败", e);
            throw new RuntimeException("委派任务失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferTask(String taskId, String userId) {
        log.debug("转办任务: taskId={}, userId={}", taskId, userId);
        
        try {
            // 转办任务（设置新的办理人）
            flowableTaskService.setAssignee(taskId, userId);
        } catch (Exception e) {
            log.error("转办任务失败", e);
            throw new RuntimeException("转办任务失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setAssignee(String taskId, String userId) {
        log.debug("设置任务办理人: taskId={}, userId={}", taskId, userId);
        
        try {
            // 设置办理人
            flowableTaskService.setAssignee(taskId, userId);
        } catch (Exception e) {
            log.error("设置任务办理人失败", e);
            throw new RuntimeException("设置任务办理人失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCandidateUser(String taskId, String userId) {
        log.debug("添加任务候选人: taskId={}, userId={}", taskId, userId);
        
        try {
            // 添加候选人
            flowableTaskService.addCandidateUser(taskId, userId);
        } catch (Exception e) {
            log.error("添加任务候选人失败", e);
            throw new RuntimeException("添加任务候选人失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCandidateUser(String taskId, String userId) {
        log.debug("删除任务候选人: taskId={}, userId={}", taskId, userId);
        
        try {
            // 删除候选人
            flowableTaskService.deleteCandidateUser(taskId, userId);
        } catch (Exception e) {
            log.error("删除任务候选人失败", e);
            throw new RuntimeException("删除任务候选人失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCandidateGroup(String taskId, String groupId) {
        log.debug("添加任务候选组: taskId={}, groupId={}", taskId, groupId);
        
        try {
            // 添加候选组
            flowableTaskService.addCandidateGroup(taskId, groupId);
        } catch (Exception e) {
            log.error("添加任务候选组失败", e);
            throw new RuntimeException("添加任务候选组失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCandidateGroup(String taskId, String groupId) {
        log.debug("删除任务候选组: taskId={}, groupId={}", taskId, groupId);
        
        try {
            // 删除候选组
            flowableTaskService.deleteCandidateGroup(taskId, groupId);
        } catch (Exception e) {
            log.error("删除任务候选组失败", e);
            throw new RuntimeException("删除任务候选组失败：" + e.getMessage());
        }
    }

    @Override
    public Map<String, Object> getTaskVariables(String taskId) {
        log.debug("获取任务变量: taskId={}", taskId);
        
        try {
            // 获取任务变量
            return flowableTaskService.getVariables(taskId);
        } catch (Exception e) {
            log.error("获取任务变量失败", e);
            throw new RuntimeException("获取任务变量失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setTaskVariables(String taskId, Map<String, Object> variables) {
        log.debug("设置任务变量: taskId={}, variables={}", taskId, variables);
        
        try {
            // 设置任务变量
            flowableTaskService.setVariables(taskId, variables);
        } catch (Exception e) {
            log.error("设置任务变量失败", e);
            throw new RuntimeException("设置任务变量失败：" + e.getMessage());
        }
    }

    @Override
    public String getTaskFormKey(String taskId) {
        log.debug("获取任务表单标识: taskId={}", taskId);
        
        try {
            // 查询任务
            Task task = flowableTaskService.createTaskQuery()
                    .taskId(taskId)
                    .singleResult();
            
            if (task == null) {
                return null;
            }
            
            // 获取表单标识
            return task.getFormKey();
        } catch (Exception e) {
            log.error("获取任务表单标识失败", e);
            throw new RuntimeException("获取任务表单标识失败：" + e.getMessage());
        }
    }

    @Override
    public List<CommentVO> getComments(String taskId) {
        log.debug("获取任务评论: taskId={}", taskId);
        
        try {
            // 查询任务评论
            return FlowableUtils.toCommentVOListFromComments(flowableTaskService.getTaskComments(taskId));
        } catch (Exception e) {
            log.error("获取任务评论失败", e);
            throw new RuntimeException("获取任务评论失败：" + e.getMessage());
        }
    }

    @Override
    public List<CommentVO> getProcessInstanceComments(String processInstanceId) {
        log.debug("获取流程实例评论: processInstanceId={}", processInstanceId);
        
        try {
            // 查询流程实例评论
            return FlowableUtils.toCommentVOListFromComments(flowableTaskService.getProcessInstanceComments(processInstanceId));
        } catch (Exception e) {
            log.error("获取流程实例评论失败", e);
            throw new RuntimeException("获取流程实例评论失败：" + e.getMessage());
        }
    }

    /**
     * 解决任务（扩展方法）
     */
    @Transactional(rollbackFor = Exception.class)
    public void resolveTask(String taskId) {
        log.debug("解决任务: taskId={}", taskId);
        
        try {
            // 解决任务
            flowableTaskService.resolveTask(taskId);
        } catch (Exception e) {
            log.error("解决任务失败", e);
            throw new RuntimeException("解决任务失败：" + e.getMessage());
        }
    }

    /**
     * 移除任务变量（扩展方法）
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeTaskVariable(String taskId, String variableName) {
        log.debug("移除任务变量: taskId={}, variableName={}", taskId, variableName);
        
        try {
            // 移除任务变量
            flowableTaskService.removeVariable(taskId, variableName);
        } catch (Exception e) {
            log.error("移除任务变量失败", e);
            throw new RuntimeException("移除任务变量失败：" + e.getMessage());
        }
    }
} 