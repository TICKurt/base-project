package com.example.workflow.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * 工作流事件类
 *
 * @author Author
 * @version 1.0
 */
@Getter
@Setter
public class WorkflowEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    /**
     * 事件类型
     */
    private String type;

    /**
     * 流程定义ID
     */
    private String processDefinitionId;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 业务标识
     */
    private String businessKey;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 事件时间
     */
    private Long eventTime;

    /**
     * 事件数据
     */
    private Map<String, Object> data;

    /**
     * 构造函数
     *
     * @param source 事件源
     */
    public WorkflowEvent(Object source) {
        super(source);
        this.eventTime = System.currentTimeMillis();
    }

    /**
     * 构造函数
     *
     * @param source 事件源
     * @param type   事件类型
     */
    public WorkflowEvent(Object source, String type) {
        super(source);
        this.type = type;
        this.eventTime = System.currentTimeMillis();
    }

    /**
     * 构造函数
     *
     * @param source             事件源
     * @param type              事件类型
     * @param processDefinitionId 流程定义ID
     * @param processInstanceId   流程实例ID
     * @param taskId             任务ID
     * @param businessKey        业务标识
     * @param userId             用户ID
     * @param data              事件数据
     */
    public WorkflowEvent(Object source, String type, String processDefinitionId, String processInstanceId,
                        String taskId, String businessKey, String userId, Map<String, Object> data) {
        super(source);
        this.type = type;
        this.processDefinitionId = processDefinitionId;
        this.processInstanceId = processInstanceId;
        this.taskId = taskId;
        this.businessKey = businessKey;
        this.userId = userId;
        this.data = data;
        this.eventTime = System.currentTimeMillis();
    }
} 