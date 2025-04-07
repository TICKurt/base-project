package com.example.workflow.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 工作流事件监听器类
 *
 * @author Author
 * @version 1.0
 */
@Slf4j
@Component
public class WorkflowEventListener {

    /**
     * 处理流程定义相关事件
     *
     * @param event 事件对象
     */
    @Async
    @EventListener(condition = "#event.type.startsWith('PROCESS_DEFINITION_')")
    public void handleProcessDefinitionEvent(WorkflowEvent event) {
        log.info("处理流程定义事件：type={}, processDefinitionId={}, userId={}",
                event.getType(), event.getProcessDefinitionId(), event.getUserId());

        // TODO: 处理流程定义事件
    }

    /**
     * 处理流程实例相关事件
     *
     * @param event 事件对象
     */
    @Async
    @EventListener(condition = "#event.type.startsWith('PROCESS_INSTANCE_')")
    public void handleProcessInstanceEvent(WorkflowEvent event) {
        log.info("处理流程实例事件：type={}, processInstanceId={}, businessKey={}, userId={}",
                event.getType(), event.getProcessInstanceId(), event.getBusinessKey(), event.getUserId());

        // TODO: 处理流程实例事件
    }

    /**
     * 处理任务相关事件
     *
     * @param event 事件对象
     */
    @Async
    @EventListener(condition = "#event.type.startsWith('TASK_')")
    public void handleTaskEvent(WorkflowEvent event) {
        log.info("处理任务事件：type={}, taskId={}, processInstanceId={}, userId={}",
                event.getType(), event.getTaskId(), event.getProcessInstanceId(), event.getUserId());

        // TODO: 处理任务事件
    }

    /**
     * 处理表单相关事件
     *
     * @param event 事件对象
     */
    @Async
    @EventListener(condition = "#event.type.startsWith('FORM_')")
    public void handleFormEvent(WorkflowEvent event) {
        log.info("处理表单事件：type={}, businessKey={}, userId={}",
                event.getType(), event.getBusinessKey(), event.getUserId());

        // TODO: 处理表单事件
    }
} 