package com.example.workflow.enums;

import lombok.Getter;

/**
 * 工作流枚举类
 *
 * @author Author
 * @version 1.0
 */
public class WorkflowEnum {

    /**
     * 流程定义状态枚举
     */
    @Getter
    public enum ProcessDefinitionState {
        /**
         * 激活
         */
        ACTIVE(1, "激活"),

        /**
         * 挂起
         */
        SUSPENDED(2, "挂起");

        private final int code;
        private final String desc;

        ProcessDefinitionState(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 流程实例状态枚举
     */
    @Getter
    public enum ProcessInstanceState {
        /**
         * 进行中
         */
        RUNNING(1, "进行中"),

        /**
         * 已完成
         */
        FINISHED(2, "已完成"),

        /**
         * 已终止
         */
        TERMINATED(3, "已终止"),

        /**
         * 已挂起
         */
        SUSPENDED(4, "已挂起");

        private final int code;
        private final String desc;

        ProcessInstanceState(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 任务状态枚举
     */
    @Getter
    public enum TaskState {
        /**
         * 待办
         */
        TODO(1, "待办"),

        /**
         * 已完成
         */
        FINISHED(2, "已完成"),

        /**
         * 已终止
         */
        TERMINATED(3, "已终止"),

        /**
         * 已挂起
         */
        SUSPENDED(4, "已挂起");

        private final int code;
        private final String desc;

        TaskState(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 任务类型枚举
     */
    @Getter
    public enum TaskType {
        /**
         * 用户任务
         */
        USER_TASK("userTask", "用户任务"),

        /**
         * 服务任务
         */
        SERVICE_TASK("serviceTask", "服务任务"),

        /**
         * 脚本任务
         */
        SCRIPT_TASK("scriptTask", "脚本任务"),

        /**
         * 接收任务
         */
        RECEIVE_TASK("receiveTask", "接收任务"),

        /**
         * 手动任务
         */
        MANUAL_TASK("manualTask", "手动任务"),

        /**
         * 业务规则任务
         */
        BUSINESS_RULE_TASK("businessRuleTask", "业务规则任务"),

        /**
         * 邮件任务
         */
        MAIL_TASK("mailTask", "邮件任务"),

        /**
         * 调用活动
         */
        CALL_ACTIVITY("callActivity", "调用活动");

        private final String code;
        private final String desc;

        TaskType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 表单状态枚举
     */
    @Getter
    public enum FormState {
        /**
         * 草稿
         */
        DRAFT(1, "草稿"),

        /**
         * 已发布
         */
        PUBLISHED(2, "已发布"),

        /**
         * 已禁用
         */
        DISABLED(3, "已禁用");

        private final int code;
        private final String desc;

        FormState(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 表单类型枚举
     */
    @Getter
    public enum FormType {
        /**
         * 流程表单
         */
        PROCESS("process", "流程表单"),

        /**
         * 任务表单
         */
        TASK("task", "任务表单");

        private final String code;
        private final String desc;

        FormType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 变量类型枚举
     */
    @Getter
    public enum VariableType {
        /**
         * 字符串
         */
        STRING("string", "字符串"),

        /**
         * 整数
         */
        INTEGER("integer", "整数"),

        /**
         * 长整数
         */
        LONG("long", "长整数"),

        /**
         * 双精度浮点数
         */
        DOUBLE("double", "双精度浮点数"),

        /**
         * 布尔值
         */
        BOOLEAN("boolean", "布尔值"),

        /**
         * 日期
         */
        DATE("date", "日期"),

        /**
         * JSON对象
         */
        JSON("json", "JSON对象");

        private final String code;
        private final String desc;

        VariableType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
} 