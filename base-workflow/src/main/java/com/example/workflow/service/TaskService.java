package com.example.workflow.service;

import com.example.workflow.model.vo.CommentVO;
import com.example.workflow.model.vo.TaskVO;

import java.util.List;
import java.util.Map;

/**
 * 任务服务接口
 *
 * @author Author
 * @version 1.0
 */
public interface TaskService {

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
    List<TaskVO> listTasks(String assignee, String candidateUser, String candidateGroup, String processInstanceId, String processDefinitionKey);

    /**
     * 获取任务详情
     *
     * @param taskId 任务ID
     * @return 任务详情
     */
    TaskVO getTaskById(String taskId);

    /**
     * 完成任务
     *
     * @param taskId 任务ID
     * @param variables 任务变量
     */
    void completeTask(String taskId, Map<String, Object> variables);

    /**
     * 签收任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     */
    void claimTask(String taskId, String userId);

    /**
     * 取消签收任务
     *
     * @param taskId 任务ID
     */
    void unclaimTask(String taskId);

    /**
     * 委派任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     */
    void delegateTask(String taskId, String userId);

    /**
     * 转办任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     */
    void transferTask(String taskId, String userId);

    /**
     * 设置任务处理人
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     */
    void setAssignee(String taskId, String userId);

    /**
     * 添加候选人
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     */
    void addCandidateUser(String taskId, String userId);

    /**
     * 删除候选人
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     */
    void deleteCandidateUser(String taskId, String userId);

    /**
     * 添加候选组
     *
     * @param taskId 任务ID
     * @param groupId 组ID
     */
    void addCandidateGroup(String taskId, String groupId);

    /**
     * 删除候选组
     *
     * @param taskId 任务ID
     * @param groupId 组ID
     */
    void deleteCandidateGroup(String taskId, String groupId);

    /**
     * 获取任务变量
     *
     * @param taskId 任务ID
     * @return 任务变量
     */
    Map<String, Object> getTaskVariables(String taskId);

    /**
     * 设置任务变量
     *
     * @param taskId 任务ID
     * @param variables 任务变量
     */
    void setTaskVariables(String taskId, Map<String, Object> variables);

    /**
     * 获取任务表单标识
     *
     * @param taskId 任务ID
     * @return 任务表单标识
     */
    String getTaskFormKey(String taskId);

    /**
     * 获取任务评论列表
     *
     * @param taskId 任务ID
     * @return 评论列表
     */
    List<CommentVO> getComments(String taskId);

    /**
     * 获取流程实例的评论列表
     *
     * @param processInstanceId 流程实例ID
     * @return 评论列表
     */
    List<CommentVO> getProcessInstanceComments(String processInstanceId);
} 