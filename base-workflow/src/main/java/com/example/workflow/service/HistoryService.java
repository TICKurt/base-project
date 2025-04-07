package com.example.workflow.service;

import com.example.workflow.model.vo.CommentVO;
import com.example.workflow.model.vo.HistoricActivityInstanceVO;
import com.example.workflow.model.vo.HistoricProcessInstanceVO;
import com.example.workflow.model.vo.HistoricTaskInstanceVO;
import com.example.workflow.model.vo.ProcessInstanceVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 历史服务接口
 *
 * @author Author
 * @version 1.0
 */
public interface HistoryService {

    /**
     * 查询历史流程实例列表
     *
     * @param processDefinitionKey 流程定义标识
     * @param businessKey 业务标识
     * @param startUserId 发起人ID
     * @param finished 是否已完成
     * @return 历史流程实例列表
     */
    List<HistoricProcessInstanceVO> listHistoricProcessInstances(String processDefinitionKey, String businessKey, String startUserId, Boolean finished);

    /**
     * 获取历史流程实例详情
     *
     * @param processInstanceId 流程实例ID
     * @return 历史流程实例详情
     */
    HistoricProcessInstanceVO getHistoricProcessInstanceById(String processInstanceId);

    /**
     * 查询历史任务列表
     *
     * @param processInstanceId 流程实例ID
     * @param taskName 任务名称
     * @param assignee 办理人
     * @param finished 是否已完成
     * @return 历史任务列表
     */
    List<HistoricTaskInstanceVO> listHistoricTasks(String processInstanceId, String taskName, String assignee, Boolean finished);

    /**
     * 获取历史任务详情
     *
     * @param taskId 任务ID
     * @return 历史任务详情
     */
    HistoricTaskInstanceVO getHistoricTaskById(String taskId);

    /**
     * 获取历史流程变量
     *
     * @param processInstanceId 流程实例ID
     * @return 历史流程变量
     */
    Map<String, Object> getHistoricProcessVariables(String processInstanceId);

    /**
     * 获取历史任务表单数据
     *
     * @param taskId 任务ID
     * @return 历史任务表单数据
     */
    Map<String, Object> getHistoricTaskFormData(String taskId);

    /**
     * 删除历史流程实例
     *
     * @param processInstanceId 流程实例ID
     */
    void deleteHistoricProcessInstance(String processInstanceId);

    /**
     * 获取历史活动列表
     *
     * @param processInstanceId 流程实例ID
     * @param activityType 活动类型
     * @param finished 是否已完成
     * @return 历史活动列表
     */
    List<HistoricActivityInstanceVO> listHistoricActivities(String processInstanceId, String activityType, Boolean finished);

    /**
     * 获取历史活动详情
     *
     * @param activityInstanceId 活动实例ID
     * @return 历史活动详情
     */
    HistoricActivityInstanceVO getHistoricActivity(String activityInstanceId);

    /**
     * 获取历史任务变量
     *
     * @param taskId 任务ID
     * @return 变量Map
     */
    Map<String, Object> getHistoricTaskVariables(String taskId);

    /**
     * 获取历史评论列表
     *
     * @param processInstanceId 流程实例ID
     * @param taskId 任务ID
     * @return 评论列表
     */
    List<CommentVO> listHistoricComments(String processInstanceId, String taskId);

    /**
     * 获取流程实例的耗时统计
     *
     * @param processInstanceId 流程实例ID
     * @return 各节点耗时统计（key为活动ID，value为耗时，单位毫秒）
     */
    Map<String, Long> getProcessInstanceDuration(String processInstanceId);

    /**
     * 获取流程实例的处理人统计
     *
     * @param processInstanceId 流程实例ID
     * @return 各节点处理人统计（key为活动ID，value为处理人ID列表）
     */
    Map<String, List<String>> getProcessInstanceHandlers(String processInstanceId);
} 