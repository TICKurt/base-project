package com.example.workflow.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 工作流事件发布器类
 *
 * @author Author
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class WorkflowEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * 发布事件
     *
     * @param event 事件对象
     */
    public void publishEvent(WorkflowEvent event) {
        eventPublisher.publishEvent(event);
    }

    /**
     * 发布流程定义事件
     *
     * @param type              事件类型
     * @param processDefinitionId 流程定义ID
     * @param userId             用户ID
     * @param data              事件数据
     */
    public void publishProcessDefinitionEvent(String type, String processDefinitionId, String userId,
                                            Map<String, Object> data) {
        WorkflowEvent event = new WorkflowEvent(this, type);
        event.setProcessDefinitionId(processDefinitionId);
        event.setUserId(userId);
        event.setData(data);
        publishEvent(event);
    }

    /**
     * 发布流程实例事件
     *
     * @param type              事件类型
     * @param processDefinitionId 流程定义ID
     * @param processInstanceId   流程实例ID
     * @param businessKey        业务标识
     * @param userId             用户ID
     * @param data              事件数据
     */
    public void publishProcessInstanceEvent(String type, String processDefinitionId, String processInstanceId,
                                          String businessKey, String userId, Map<String, Object> data) {
        WorkflowEvent event = new WorkflowEvent(this, type);
        event.setProcessDefinitionId(processDefinitionId);
        event.setProcessInstanceId(processInstanceId);
        event.setBusinessKey(businessKey);
        event.setUserId(userId);
        event.setData(data);
        publishEvent(event);
    }

    /**
     * 发布任务事件
     *
     * @param type              事件类型
     * @param processDefinitionId 流程定义ID
     * @param processInstanceId   流程实例ID
     * @param taskId             任务ID
     * @param businessKey        业务标识
     * @param userId             用户ID
     * @param data              事件数据
     */
    public void publishTaskEvent(String type, String processDefinitionId, String processInstanceId,
                               String taskId, String businessKey, String userId, Map<String, Object> data) {
        WorkflowEvent event = new WorkflowEvent(this, type);
        event.setProcessDefinitionId(processDefinitionId);
        event.setProcessInstanceId(processInstanceId);
        event.setTaskId(taskId);
        event.setBusinessKey(businessKey);
        event.setUserId(userId);
        event.setData(data);
        publishEvent(event);
    }

    /**
     * 发布表单事件
     *
     * @param type         事件类型
     * @param businessKey  业务标识
     * @param userId       用户ID
     * @param data        事件数据
     */
    public void publishFormEvent(String type, String businessKey, String userId, Map<String, Object> data) {
        WorkflowEvent event = new WorkflowEvent(this, type);
        event.setBusinessKey(businessKey);
        event.setUserId(userId);
        event.setData(data);
        publishEvent(event);
    }
} 