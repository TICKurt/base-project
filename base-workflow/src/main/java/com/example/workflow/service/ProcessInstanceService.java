package com.example.workflow.service;

import com.example.workflow.model.vo.ProcessInstanceVO;

import java.util.List;
import java.util.Map;

/**
 * 流程实例服务接口
 *
 * @author Author
 * @version 1.0
 */
public interface ProcessInstanceService {

    /**
     * 启动流程实例
     *
     * @param processDefinitionId 流程定义ID
     * @param businessKey 业务标识
     * @param variables 流程变量
     * @return 流程实例ID
     */
    String startProcessInstance(String processDefinitionId, String businessKey, Map<String, Object> variables);

    /**
     * 启动流程实例（通过流程定义标识）
     *
     * @param processDefinitionKey 流程定义标识
     * @param businessKey 业务标识
     * @param variables 流程变量
     * @return 流程实例ID
     */
    String startProcessInstanceByKey(String processDefinitionKey, String businessKey, Map<String, Object> variables);

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
    List<ProcessInstanceVO> listProcessInstances(String processDefinitionKey, String businessKey, String startUserId, Boolean active, Boolean suspended);

    /**
     * 分页查询流程实例列表
     *
     * @param processDefinitionKey 流程定义标识
     * @param businessKey 业务标识
     * @param startUserId 发起人ID
     * @param active 是否活动
     * @param suspended 是否挂起
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页数量
     * @return 流程实例列表
     */
    List<ProcessInstanceVO> listProcessInstancesByPage(String processDefinitionKey, String businessKey, String startUserId, Boolean active, Boolean suspended, int pageNum, int pageSize);

    /**
     * 统计符合条件的流程实例数量
     *
     * @param processDefinitionKey 流程定义标识
     * @param businessKey 业务标识
     * @param startUserId 发起人ID
     * @param active 是否活动
     * @param suspended 是否挂起
     * @return 流程实例数量
     */
    long countProcessInstances(String processDefinitionKey, String businessKey, String startUserId, Boolean active, Boolean suspended);

    /**
     * 获取流程实例详情
     *
     * @param processInstanceId 流程实例ID
     * @return 流程实例详情
     */
    ProcessInstanceVO getProcessInstanceById(String processInstanceId);

    /**
     * 根据业务标识获取流程实例
     *
     * @param businessKey 业务标识
     * @return 流程实例列表
     */
    List<ProcessInstanceVO> getProcessInstancesByBusinessKey(String businessKey);

    /**
     * 挂起流程实例
     *
     * @param processInstanceId 流程实例ID
     */
    void suspendProcessInstance(String processInstanceId);

    /**
     * 激活流程实例
     *
     * @param processInstanceId 流程实例ID
     */
    void activateProcessInstance(String processInstanceId);

    /**
     * 删除流程实例
     *
     * @param processInstanceId 流程实例ID
     * @param deleteReason 删除原因
     */
    void deleteProcessInstance(String processInstanceId, String deleteReason);

    /**
     * 获取流程实例变量
     *
     * @param processInstanceId 流程实例ID
     * @return 变量集合
     */
    Map<String, Object> getProcessInstanceVariables(String processInstanceId);

    /**
     * 设置流程实例变量
     *
     * @param processInstanceId 流程实例ID
     * @param variables 变量集合
     */
    void setProcessInstanceVariables(String processInstanceId, Map<String, Object> variables);
} 