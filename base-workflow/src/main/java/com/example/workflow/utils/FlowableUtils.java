package com.example.workflow.utils;

import com.example.workflow.model.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Flowable对象转换工具类
 *
 * @author Author
 * @version 1.0
 */
@Slf4j
public class FlowableUtils {

    /**
     * 将流程定义对象转换为VO对象
     *
     * @param processDefinition 流程定义对象
     * @return VO对象
     */
    public static ProcessDefinitionVO toProcessDefinitionVO(ProcessDefinition processDefinition) {
        if (processDefinition == null) {
            return null;
        }

        ProcessDefinitionVO vo = new ProcessDefinitionVO();
        vo.setId(processDefinition.getId());
        vo.setKey(processDefinition.getKey());
        vo.setName(processDefinition.getName());
        vo.setCategory(processDefinition.getCategory());
        vo.setVersion(processDefinition.getVersion());
        vo.setDescription(processDefinition.getDescription());
        vo.setDeploymentId(processDefinition.getDeploymentId());
        vo.setDiagramResourceName(processDefinition.getDiagramResourceName());
        vo.setResourceName(processDefinition.getResourceName());
        vo.setTenantId(processDefinition.getTenantId());
        vo.setSuspended(processDefinition.isSuspended());
        return vo;
    }

    /**
     * 将流程实例对象转换为VO对象
     *
     * @param processInstance 流程实例对象
     * @return VO对象
     */
    public static ProcessInstanceVO toProcessInstanceVO(ProcessInstance processInstance) {
        if (processInstance == null) {
            return null;
        }

        ProcessInstanceVO vo = new ProcessInstanceVO();
        vo.setId(processInstance.getId());
        vo.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        vo.setProcessDefinitionKey(processInstance.getProcessDefinitionKey());
        vo.setProcessDefinitionName(processInstance.getProcessDefinitionName());
        vo.setProcessDefinitionVersion(processInstance.getProcessDefinitionVersion());
        vo.setDeploymentId(processInstance.getDeploymentId());
        vo.setBusinessKey(processInstance.getBusinessKey());
        vo.setName(processInstance.getName());
        vo.setDescription(processInstance.getDescription());
        vo.setStartTime(processInstance.getStartTime());
        vo.setStartUserId(processInstance.getStartUserId());
        vo.setTenantId(processInstance.getTenantId());
        vo.setSuspended(processInstance.isSuspended());
        vo.setEnded(processInstance.isEnded());
        return vo;
    }

    /**
     * 将历史流程实例对象转换为VO对象
     *
     * @param historicProcessInstance 历史流程实例对象
     * @return VO对象
     */
    public static HistoricProcessInstanceVO toHistoricProcessInstanceVO(HistoricProcessInstance historicProcessInstance) {
        if (historicProcessInstance == null) {
            return null;
        }

        HistoricProcessInstanceVO vo = new HistoricProcessInstanceVO();
        vo.setId(historicProcessInstance.getId());
        vo.setProcessDefinitionId(historicProcessInstance.getProcessDefinitionId());
        vo.setProcessDefinitionKey(historicProcessInstance.getProcessDefinitionKey());
        vo.setProcessDefinitionName(historicProcessInstance.getProcessDefinitionName());
        vo.setProcessDefinitionVersion(historicProcessInstance.getProcessDefinitionVersion());
        vo.setDeploymentId(historicProcessInstance.getDeploymentId());
        vo.setBusinessKey(historicProcessInstance.getBusinessKey());
        vo.setName(historicProcessInstance.getName());
        vo.setDescription(historicProcessInstance.getDescription());
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
     * 将任务对象转换为VO对象
     *
     * @param task 任务对象
     * @return VO对象
     */
    public static TaskVO toTaskVO(Task task) {
        if (task == null) {
            return null;
        }

        TaskVO vo = new TaskVO();
        vo.setId(task.getId());
        vo.setName(task.getName());
        vo.setDescription(task.getDescription());
        vo.setProcessInstanceId(task.getProcessInstanceId());
        vo.setProcessDefinitionId(task.getProcessDefinitionId());
        vo.setTaskDefinitionKey(task.getTaskDefinitionKey());
        vo.setFormKey(task.getFormKey());
        vo.setAssignee(task.getAssignee());
        vo.setOwner(task.getOwner());
        vo.setDelegationState(task.getDelegationState() != null ? task.getDelegationState().toString() : null);
        vo.setDueDate(task.getDueDate());
        vo.setPriority(task.getPriority());
        vo.setParentTaskId(task.getParentTaskId());
        vo.setTenantId(task.getTenantId());
        vo.setCategory(task.getCategory());
        vo.setCreateTime(task.getCreateTime());
        vo.setSuspended(task.isSuspended());
        return vo;
    }

    /**
     * 将历史任务对象转换为VO对象
     *
     * @param historicTaskInstance 历史任务对象
     * @return VO对象
     */
    public static HistoricTaskInstanceVO toHistoricTaskInstanceVO(HistoricTaskInstance historicTaskInstance) {
        if (historicTaskInstance == null) {
            return null;
        }

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
//        vo.setDelegationState(historicTaskInstance.getDelegationState() != null ? historicTaskInstance.getDelegationState().toString() : null);
        vo.setDueDate(historicTaskInstance.getDueDate());
//        vo.setFollowUpDate(historicTaskInstance.getFollowUpDate());
        vo.setPriority(historicTaskInstance.getPriority());
        vo.setParentTaskId(historicTaskInstance.getParentTaskId());
        vo.setTenantId(historicTaskInstance.getTenantId());
        vo.setCategory(historicTaskInstance.getCategory());
        vo.setCreateTime(historicTaskInstance.getCreateTime());
        vo.setEndTime(historicTaskInstance.getEndTime());
        vo.setDurationInMillis(historicTaskInstance.getDurationInMillis());
        vo.setDeleteReason(historicTaskInstance.getDeleteReason());
        return vo;
    }

    /**
     * 将评论对象列表转换为VO对象列表
     *
     * @param comments 评论对象列表
     * @return VO对象列表
     */
    public static List<CommentVO> toCommentVOList(List<org.flowable.task.api.TaskInfo> comments) {
        if (comments == null) {
            return new ArrayList<>();
        }

        return comments.stream()
                .map(comment -> {
                    CommentVO vo = new CommentVO();
                    vo.setId(comment.getId());
//                    vo.setTaskId(comment.getTaskId());
                    vo.setProcessInstanceId(comment.getProcessInstanceId());
                    vo.setUserId(comment.getAssignee());
                    vo.setTime(comment.getCreateTime());
                    vo.setFullMessage(comment.getDescription());
                    return vo;
                })
                .collect(Collectors.toList());
    }

    /**
     * 将Flowable Comment对象列表转换为VO对象列表
     *
     * @param comments Flowable Comment对象列表
     * @return VO对象列表
     */
    public static List<CommentVO> toCommentVOListFromComments(List<org.flowable.engine.task.Comment> comments) {
        if (comments == null) {
            return new ArrayList<>();
        }

        return comments.stream()
                .map(comment -> {
                    CommentVO vo = new CommentVO();
                    vo.setId(comment.getId());
                    vo.setTaskId(comment.getTaskId());
                    vo.setProcessInstanceId(comment.getProcessInstanceId());
                    vo.setUserId(comment.getUserId());
                    vo.setTime(comment.getTime());
                    vo.setFullMessage(comment.getFullMessage());
                    return vo;
                })
                .collect(Collectors.toList());
    }
} 