package com.example.workflow.service;

import com.example.workflow.model.dto.ProcessInstanceDTO;
import org.flowable.engine.runtime.ProcessInstanceQuery;

import java.util.List;
import java.util.Map;

/**
 * 流程实例服务接口
 *
 * @author Author
 * @version 1.0
 */
public interface IProcessInstanceService {

    /**
     * 启动流程实例
     *
     * @param processDefinitionId 流程定义ID
     * @param businessKey 业务标识
     * @param variables 流程变量
     * @return 流程实例信息
     */
    ProcessInstanceDTO startProcess(String processDefinitionId, String businessKey, Map<String, Object> variables);

    /**
     * 启动流程实例（通过流程定义KEY）
     *
     * @param processDefinitionKey 流程定义KEY
     * @param businessKey 业务标识
     * @param variables 流程变量
     * @return 流程实例信息
     */
    ProcessInstanceDTO startProcessByKey(String processDefinitionKey, String businessKey, Map<String, Object> variables);

    /**
     * 查询流程实例列表
     *
     * @param query 查询参数
     * @return 流程实例列表
     */
    List<ProcessInstanceDTO> list(ProcessInstanceQuery query);

    /**
     * 获取流程实例详情
     *
     * @param processInstanceId 流程实例ID
     * @return 流程实例详情
     */
    ProcessInstanceDTO getById(String processInstanceId);

    /**
     * 挂起流程实例
     *
     * @param processInstanceId 流程实例ID
     */
    void suspend(String processInstanceId);

    /**
     * 激活流程实例
     *
     * @param processInstanceId 流程实例ID
     */
    void activate(String processInstanceId);

    /**
     * 删除流程实例
     *
     * @param processInstanceId 流程实例ID
     * @param deleteReason 删除原因
     */
    void delete(String processInstanceId, String deleteReason);

    /**
     * 获取流程实例的流程图，并标记当前节点位置
     *
     * @param processInstanceId 流程实例ID
     * @return 流程图字节数组
     */
    byte[] getProcessDiagram(String processInstanceId);

    /**
     * 获取流程实例的变量
     *
     * @param processInstanceId 流程实例ID
     * @return 变量Map
     */
    Map<String, Object> getVariables(String processInstanceId);

    /**
     * 设置流程实例的变量
     *
     * @param processInstanceId 流程实例ID
     * @param variables 变量Map
     */
    void setVariables(String processInstanceId, Map<String, Object> variables);

    /**
     * 获取流程实例的活动节点
     *
     * @param processInstanceId 流程实例ID
     * @return 活动节点ID列表
     */
    List<String> getActiveActivityIds(String processInstanceId);
} 