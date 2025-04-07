package com.example.workflow.service.impl;

import com.example.workflow.model.vo.CommentVO;
import com.example.workflow.model.vo.HistoricActivityInstanceVO;
import com.example.workflow.model.vo.HistoricProcessInstanceVO;
import com.example.workflow.model.vo.HistoricTaskInstanceVO;
import com.example.workflow.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 历史服务实现类
 *
 * @author Author
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final org.flowable.engine.HistoryService flowableHistoryService;
    private final TaskService flowableTaskService;

    @Override
    public List<HistoricProcessInstanceVO> listHistoricProcessInstances(String processDefinitionKey, String businessKey, String startUserId, Boolean finished) {
        // 创建查询对象
        org.flowable.engine.history.HistoricProcessInstanceQuery query = flowableHistoryService.createHistoricProcessInstanceQuery();

        // 设置查询条件
        if (processDefinitionKey != null && !processDefinitionKey.isEmpty()) {
            query.processDefinitionKey(processDefinitionKey);
        }
        if (businessKey != null && !businessKey.isEmpty()) {
            query.processInstanceBusinessKey(businessKey);
        }
        if (startUserId != null && !startUserId.isEmpty()) {
            query.startedBy(startUserId);
        }
        if (finished != null) {
            if (finished) {
                query.finished();
            } else {
                query.unfinished();
            }
        }

        // 执行查询
        List<HistoricProcessInstance> historicProcessInstances = query
                .orderByProcessInstanceStartTime()
                .desc()
                .list();

        // 转换为VO
        return historicProcessInstances.stream()
                .map(this::convertToHistoricProcessInstanceVO)
                .collect(Collectors.toList());
    }

    @Override
    public HistoricProcessInstanceVO getHistoricProcessInstanceById(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = flowableHistoryService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();

        if (historicProcessInstance == null) {
            return null;
        }

        return convertToHistoricProcessInstanceVO(historicProcessInstance);
    }

    @Override
    public List<HistoricTaskInstanceVO> listHistoricTasks(String processInstanceId, String taskName, String assignee, Boolean finished) {
        // 创建查询对象
        org.flowable.task.api.history.HistoricTaskInstanceQuery query = flowableHistoryService.createHistoricTaskInstanceQuery();

        // 设置查询条件
        if (processInstanceId != null && !processInstanceId.isEmpty()) {
            query.processInstanceId(processInstanceId);
        }
        if (taskName != null && !taskName.isEmpty()) {
            query.taskNameLike("%" + taskName + "%");
        }
        if (assignee != null && !assignee.isEmpty()) {
            query.taskAssignee(assignee);
        }
        if (finished != null) {
            if (finished) {
                query.finished();
            } else {
                query.unfinished();
            }
        }

        // 执行查询
        List<HistoricTaskInstance> historicTaskInstances = query
                .orderByHistoricTaskInstanceEndTime()
                .desc()
                .list();

        // 转换为VO
        return historicTaskInstances.stream()
                .map(this::convertToHistoricTaskInstanceVO)
                .collect(Collectors.toList());
    }

    @Override
    public HistoricTaskInstanceVO getHistoricTaskById(String taskId) {
        HistoricTaskInstance historicTaskInstance = flowableHistoryService.createHistoricTaskInstanceQuery()
                .taskId(taskId)
                .singleResult();

        if (historicTaskInstance == null) {
            return null;
        }

        return convertToHistoricTaskInstanceVO(historicTaskInstance);
    }

    @Override
    public Map<String, Object> getHistoricProcessVariables(String processInstanceId) {
        return flowableHistoryService.createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .list()
                .stream()
                .collect(Collectors.toMap(
                        variableInstance -> variableInstance.getVariableName(),
                        variableInstance -> variableInstance.getValue(),
                        (v1, v2) -> v2));
    }

    @Override
    public Map<String, Object> getHistoricTaskFormData(String taskId) {
        // 查询任务的表单数据
        HistoricTaskInstance historicTaskInstance = flowableHistoryService.createHistoricTaskInstanceQuery()
                .taskId(taskId)
                .includeProcessVariables()
                .singleResult();

        if (historicTaskInstance == null) {
            return new HashMap<>();
        }

        // 获取任务的表单变量
        return historicTaskInstance.getProcessVariables();
    }

    @Override
    public void deleteHistoricProcessInstance(String processInstanceId) {
        flowableHistoryService.deleteHistoricProcessInstance(processInstanceId);
    }

    @Override
    public List<HistoricActivityInstanceVO> listHistoricActivities(String processInstanceId, String activityType, Boolean finished) {
        // 创建查询对象
        org.flowable.engine.history.HistoricActivityInstanceQuery query = flowableHistoryService.createHistoricActivityInstanceQuery();

        // 设置查询条件
        if (processInstanceId != null && !processInstanceId.isEmpty()) {
            query.processInstanceId(processInstanceId);
        }
        if (activityType != null && !activityType.isEmpty()) {
            query.activityType(activityType);
        }
        if (finished != null) {
            if (finished) {
                query.finished();
            } else {
                query.unfinished();
            }
        }

        // 执行查询
        List<HistoricActivityInstance> historicActivityInstances = query
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();

        // 转换为VO
        return historicActivityInstances.stream()
                .map(this::convertToHistoricActivityInstanceVO)
                .collect(Collectors.toList());
    }

    @Override
    public HistoricActivityInstanceVO getHistoricActivity(String activityInstanceId) {
        HistoricActivityInstance historicActivityInstance = flowableHistoryService.createHistoricActivityInstanceQuery()
                .activityInstanceId(activityInstanceId)
                .singleResult();

        if (historicActivityInstance == null) {
            return null;
        }

        return convertToHistoricActivityInstanceVO(historicActivityInstance);
    }

    @Override
    public Map<String, Object> getHistoricTaskVariables(String taskId) {
        return flowableHistoryService.createHistoricVariableInstanceQuery()
                .taskId(taskId)
                .list()
                .stream()
                .collect(Collectors.toMap(
                        variableInstance -> variableInstance.getVariableName(),
                        variableInstance -> variableInstance.getValue(),
                        (v1, v2) -> v2));
    }

    @Override
    public List<CommentVO> listHistoricComments(String processInstanceId, String taskId) {
        List<org.flowable.engine.task.Comment> comments;
        if (taskId != null && !taskId.isEmpty()) {
            comments = flowableTaskService.getTaskComments(taskId);
        } else if (processInstanceId != null && !processInstanceId.isEmpty()) {
            comments = flowableTaskService.getProcessInstanceComments(processInstanceId);
        } else {
            return new ArrayList<>();
        }

        return comments.stream()
                .map(this::convertToCommentVO)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Long> getProcessInstanceDuration(String processInstanceId) {
        List<HistoricActivityInstance> activities = flowableHistoryService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .finished()
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();

        return activities.stream()
                .filter(activity -> activity.getDurationInMillis() != null)
                .collect(Collectors.toMap(
                        HistoricActivityInstance::getActivityId,
                        HistoricActivityInstance::getDurationInMillis,
                        (v1, v2) -> v1 + v2));
    }

    @Override
    public Map<String, List<String>> getProcessInstanceHandlers(String processInstanceId) {
        List<HistoricTaskInstance> tasks = flowableHistoryService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();

        Map<String, List<String>> result = new HashMap<>();
        for (HistoricTaskInstance task : tasks) {
            String activityId = task.getTaskDefinitionKey();
            String assignee = task.getAssignee();
            
            if (assignee != null && !assignee.isEmpty()) {
                List<String> handlers = result.getOrDefault(activityId, new ArrayList<>());
                if (!handlers.contains(assignee)) {
                    handlers.add(assignee);
                }
                result.put(activityId, handlers);
            }
        }

        return result;
    }

    /**
     * 将历史流程实例转换为VO
     */
    private HistoricProcessInstanceVO convertToHistoricProcessInstanceVO(HistoricProcessInstance historicProcessInstance) {
        HistoricProcessInstanceVO vo = new HistoricProcessInstanceVO();
        vo.setId(historicProcessInstance.getId());
        vo.setProcessDefinitionId(historicProcessInstance.getProcessDefinitionId());
        vo.setProcessDefinitionKey(historicProcessInstance.getProcessDefinitionKey());
        vo.setProcessDefinitionName(historicProcessInstance.getProcessDefinitionName());
        vo.setProcessDefinitionVersion(historicProcessInstance.getProcessDefinitionVersion());
        vo.setDeploymentId(historicProcessInstance.getDeploymentId());
        vo.setBusinessKey(historicProcessInstance.getBusinessKey());
        vo.setName(historicProcessInstance.getName());
        vo.setStartTime(historicProcessInstance.getStartTime());
        vo.setEndTime(historicProcessInstance.getEndTime());
        vo.setDurationInMillis(historicProcessInstance.getDurationInMillis());
        vo.setStartUserId(historicProcessInstance.getStartUserId());
        vo.setStartActivityId(historicProcessInstance.getStartActivityId());
        vo.setEndActivityId(historicProcessInstance.getEndActivityId());
        vo.setDeleteReason(historicProcessInstance.getDeleteReason());
        vo.setTenantId(historicProcessInstance.getTenantId());
        return vo;
    }

    /**
     * 将历史任务实例转换为VO
     */
    private HistoricTaskInstanceVO convertToHistoricTaskInstanceVO(HistoricTaskInstance historicTaskInstance) {
        HistoricTaskInstanceVO vo = new HistoricTaskInstanceVO();
        vo.setId(historicTaskInstance.getId());
        vo.setName(historicTaskInstance.getName());
        vo.setDescription(historicTaskInstance.getDescription());
        vo.setProcessInstanceId(historicTaskInstance.getProcessInstanceId());
        vo.setProcessDefinitionId(historicTaskInstance.getProcessDefinitionId());
        vo.setTaskDefinitionKey(historicTaskInstance.getTaskDefinitionKey());
        vo.setFormKey(historicTaskInstance.getFormKey());
        vo.setAssignee(historicTaskInstance.getAssignee());
        vo.setOwner(historicTaskInstance.getOwner());
        vo.setPriority(historicTaskInstance.getPriority());
        vo.setDueDate(historicTaskInstance.getDueDate());
        vo.setCategory(historicTaskInstance.getCategory());
        vo.setCreateTime(historicTaskInstance.getCreateTime());
        vo.setEndTime(historicTaskInstance.getEndTime());
        vo.setDurationInMillis(historicTaskInstance.getDurationInMillis());
        vo.setDeleteReason(historicTaskInstance.getDeleteReason());
        vo.setParentTaskId(historicTaskInstance.getParentTaskId());
        vo.setTenantId(historicTaskInstance.getTenantId());
        return vo;
    }

    /**
     * 将历史活动实例转换为VO
     */
    private HistoricActivityInstanceVO convertToHistoricActivityInstanceVO(HistoricActivityInstance historicActivityInstance) {
        HistoricActivityInstanceVO vo = new HistoricActivityInstanceVO();
        vo.setId(historicActivityInstance.getId());
        vo.setActivityId(historicActivityInstance.getActivityId());
        vo.setActivityName(historicActivityInstance.getActivityName());
        vo.setActivityType(historicActivityInstance.getActivityType());
        vo.setProcessDefinitionId(historicActivityInstance.getProcessDefinitionId());
        vo.setProcessInstanceId(historicActivityInstance.getProcessInstanceId());
        vo.setExecutionId(historicActivityInstance.getExecutionId());
        vo.setTaskId(historicActivityInstance.getTaskId());
        vo.setAssignee(historicActivityInstance.getAssignee());
        vo.setStartTime(historicActivityInstance.getStartTime());
        vo.setEndTime(historicActivityInstance.getEndTime());
        vo.setDurationInMillis(historicActivityInstance.getDurationInMillis());
        vo.setTenantId(historicActivityInstance.getTenantId());
        return vo;
    }

    /**
     * 将评论转换为VO
     */
    private CommentVO convertToCommentVO(org.flowable.engine.task.Comment comment) {
        CommentVO vo = new CommentVO();
        vo.setId(comment.getId());
        vo.setUserId(comment.getUserId());
        vo.setTaskId(comment.getTaskId());
        vo.setProcessInstanceId(comment.getProcessInstanceId());
        vo.setMessage(comment.getFullMessage());
        vo.setTime(comment.getTime());
        return vo;
    }
} 