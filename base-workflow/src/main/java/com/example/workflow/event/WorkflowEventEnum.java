package com.example.workflow.event;

import lombok.Getter;

/**
 * 工作流事件枚举类
 *
 * @author Author
 * @version 1.0
 */
public class WorkflowEventEnum {

    /**
     * 流程定义事件类型枚举
     */
    @Getter
    public enum ProcessDefinitionEventType {
        /**
         * 部署
         */
        DEPLOY("PROCESS_DEFINITION_DEPLOY", "流程定义部署"),

        /**
         * 删除
         */
        DELETE("PROCESS_DEFINITION_DELETE", "流程定义删除"),

        /**
         * 激活
         */
        ACTIVATE("PROCESS_DEFINITION_ACTIVATE", "流程定义激活"),

        /**
         * 挂起
         */
        SUSPEND("PROCESS_DEFINITION_SUSPEND", "流程定义挂起");

        private final String code;
        private final String desc;

        ProcessDefinitionEventType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 流程实例事件类型枚举
     */
    @Getter
    public enum ProcessInstanceEventType {
        /**
         * 启动
         */
        START("PROCESS_INSTANCE_START", "流程实例启动"),

        /**
         * 完成
         */
        COMPLETE("PROCESS_INSTANCE_COMPLETE", "流程实例完成"),

        /**
         * 终止
         */
        TERMINATE("PROCESS_INSTANCE_TERMINATE", "流程实例终止"),

        /**
         * 删除
         */
        DELETE("PROCESS_INSTANCE_DELETE", "流程实例删除"),

        /**
         * 激活
         */
        ACTIVATE("PROCESS_INSTANCE_ACTIVATE", "流程实例激活"),

        /**
         * 挂起
         */
        SUSPEND("PROCESS_INSTANCE_SUSPEND", "流程实例挂起"),

        /**
         * 变量更新
         */
        VARIABLE_UPDATE("PROCESS_INSTANCE_VARIABLE_UPDATE", "流程实例变量更新");

        private final String code;
        private final String desc;

        ProcessInstanceEventType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 任务事件类型枚举
     */
    @Getter
    public enum TaskEventType {
        /**
         * 创建
         */
        CREATE("TASK_CREATE", "任务创建"),

        /**
         * 分配
         */
        ASSIGN("TASK_ASSIGN", "任务分配"),

        /**
         * 完成
         */
        COMPLETE("TASK_COMPLETE", "任务完成"),

        /**
         * 删除
         */
        DELETE("TASK_DELETE", "任务删除"),

        /**
         * 认领
         */
        CLAIM("TASK_CLAIM", "任务认领"),

        /**
         * 取消认领
         */
        UNCLAIM("TASK_UNCLAIM", "任务取消认领"),

        /**
         * 委派
         */
        DELEGATE("TASK_DELEGATE", "任务委派"),

        /**
         * 转办
         */
        TRANSFER("TASK_TRANSFER", "任务转办"),

        /**
         * 添加候选人
         */
        ADD_CANDIDATE_USER("TASK_ADD_CANDIDATE_USER", "添加任务候选人"),

        /**
         * 删除候选人
         */
        DELETE_CANDIDATE_USER("TASK_DELETE_CANDIDATE_USER", "删除任务候选人"),

        /**
         * 添加候选组
         */
        ADD_CANDIDATE_GROUP("TASK_ADD_CANDIDATE_GROUP", "添加任务候选组"),

        /**
         * 删除候选组
         */
        DELETE_CANDIDATE_GROUP("TASK_DELETE_CANDIDATE_GROUP", "删除任务候选组"),

        /**
         * 变量更新
         */
        VARIABLE_UPDATE("TASK_VARIABLE_UPDATE", "任务变量更新");

        private final String code;
        private final String desc;

        TaskEventType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 表单事件类型枚举
     */
    @Getter
    public enum FormEventType {
        /**
         * 创建
         */
        CREATE("FORM_CREATE", "表单创建"),

        /**
         * 更新
         */
        UPDATE("FORM_UPDATE", "表单更新"),

        /**
         * 删除
         */
        DELETE("FORM_DELETE", "表单删除"),

        /**
         * 发布
         */
        PUBLISH("FORM_PUBLISH", "表单发布"),

        /**
         * 禁用
         */
        DISABLE("FORM_DISABLE", "表单禁用"),

        /**
         * 数据保存
         */
        DATA_SAVE("FORM_DATA_SAVE", "表单数据保存"),

        /**
         * 数据更新
         */
        DATA_UPDATE("FORM_DATA_UPDATE", "表单数据更新"),

        /**
         * 数据删除
         */
        DATA_DELETE("FORM_DATA_DELETE", "表单数据删除");

        private final String code;
        private final String desc;

        FormEventType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 事件错误类型枚举
     */
    @Getter
    public enum EventErrorType {
        /**
         * 事件处理失败
         */
        EVENT_HANDLE_FAILED("EVENT_HANDLE_FAILED", "事件处理失败"),

        /**
         * 事件发布失败
         */
        EVENT_PUBLISH_FAILED("EVENT_PUBLISH_FAILED", "事件发布失败"),

        /**
         * 事件类型无效
         */
        INVALID_EVENT_TYPE("INVALID_EVENT_TYPE", "事件类型无效"),

        /**
         * 事件数据无效
         */
        INVALID_EVENT_DATA("INVALID_EVENT_DATA", "事件数据无效");

        private final String code;
        private final String desc;

        EventErrorType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
} 