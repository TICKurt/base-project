package com.example.workflow.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * 评论VO
 *
 * @author Author
 * @version 1.0
 */
@Data
public class CommentVO {

    /**
     * 评论ID
     */
    private String id;

    /**
     * 评论类型
     */
    private String type;

    /**
     * 评论时间
     */
    private Date time;

    /**
     * 评论用户ID
     */
    private String userId;

    /**
     * 评论用户名称
     */
    private String userName;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 评论动作
     */
    private String action;

    /**
     * 评论消息
     */
    private String message;

    /**
     * 完整消息
     */
    private String fullMessage;

    /**
     * 租户ID
     */
    private String tenantId;
} 