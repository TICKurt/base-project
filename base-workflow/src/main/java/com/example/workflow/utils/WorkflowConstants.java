package com.example.workflow.utils;

/**
 * 工作流常量类
 *
 * @author Author
 * @version 1.0
 */
public class WorkflowConstants {

    /**
     * 流程定义相关常量
     */
    public static class ProcessDefinition {
        /**
         * 流程定义状态：激活
         */
        public static final int STATE_ACTIVE = 1;

        /**
         * 流程定义状态：挂起
         */
        public static final int STATE_SUSPENDED = 2;

        /**
         * 流程定义类型：BPMN
         */
        public static final String TYPE_BPMN = "bpmn";

        /**
         * 流程定义类型：FORM
         */
        public static final String TYPE_FORM = "form";
    }

    /**
     * 流程实例相关常量
     */
    public static class ProcessInstance {
        /**
         * 流程实例状态：进行中
         */
        public static final int STATE_RUNNING = 1;

        /**
         * 流程实例状态：已完成
         */
        public static final int STATE_FINISHED = 2;

        /**
         * 流程实例状态：已终止
         */
        public static final int STATE_TERMINATED = 3;

        /**
         * 流程实例状态：已挂起
         */
        public static final int STATE_SUSPENDED = 4;
    }

    /**
     * 任务相关常量
     */
    public static class Task {
        /**
         * 任务状态：待办
         */
        public static final int STATE_TODO = 1;

        /**
         * 任务状态：已完成
         */
        public static final int STATE_FINISHED = 2;

        /**
         * 任务状态：已终止
         */
        public static final int STATE_TERMINATED = 3;

        /**
         * 任务状态：已挂起
         */
        public static final int STATE_SUSPENDED = 4;

        /**
         * 任务类型：用户任务
         */
        public static final String TYPE_USER_TASK = "userTask";

        /**
         * 任务类型：服务任务
         */
        public static final String TYPE_SERVICE_TASK = "serviceTask";

        /**
         * 任务类型：脚本任务
         */
        public static final String TYPE_SCRIPT_TASK = "scriptTask";

        /**
         * 任务类型：接收任务
         */
        public static final String TYPE_RECEIVE_TASK = "receiveTask";

        /**
         * 任务类型：手动任务
         */
        public static final String TYPE_MANUAL_TASK = "manualTask";

        /**
         * 任务类型：业务规则任务
         */
        public static final String TYPE_BUSINESS_RULE_TASK = "businessRuleTask";

        /**
         * 任务类型：邮件任务
         */
        public static final String TYPE_MAIL_TASK = "mailTask";

        /**
         * 任务类型：调用活动
         */
        public static final String TYPE_CALL_ACTIVITY = "callActivity";
    }

    /**
     * 表单相关常量
     */
    public static class Form {
        /**
         * 表单状态：草稿
         */
        public static final int STATE_DRAFT = 1;

        /**
         * 表单状态：已发布
         */
        public static final int STATE_PUBLISHED = 2;

        /**
         * 表单状态：已禁用
         */
        public static final int STATE_DISABLED = 3;

        /**
         * 表单类型：流程表单
         */
        public static final String TYPE_PROCESS = "process";

        /**
         * 表单类型：任务表单
         */
        public static final String TYPE_TASK = "task";
    }

    /**
     * 变量相关常量
     */
    public static class Variable {
        /**
         * 变量类型：字符串
         */
        public static final String TYPE_STRING = "string";

        /**
         * 变量类型：整数
         */
        public static final String TYPE_INTEGER = "integer";

        /**
         * 变量类型：长整数
         */
        public static final String TYPE_LONG = "long";

        /**
         * 变量类型：双精度浮点数
         */
        public static final String TYPE_DOUBLE = "double";

        /**
         * 变量类型：布尔值
         */
        public static final String TYPE_BOOLEAN = "boolean";

        /**
         * 变量类型：日期
         */
        public static final String TYPE_DATE = "date";

        /**
         * 变量类型：JSON对象
         */
        public static final String TYPE_JSON = "json";
    }

    /**
     * 错误码常量
     */
    public static class ErrorCode {
        /**
         * 流程定义不存在
         */
        public static final String PROCESS_DEFINITION_NOT_FOUND = "PROCESS_DEFINITION_NOT_FOUND";

        /**
         * 流程实例不存在
         */
        public static final String PROCESS_INSTANCE_NOT_FOUND = "PROCESS_INSTANCE_NOT_FOUND";

        /**
         * 任务不存在
         */
        public static final String TASK_NOT_FOUND = "TASK_NOT_FOUND";

        /**
         * 表单不存在
         */
        public static final String FORM_NOT_FOUND = "FORM_NOT_FOUND";

        /**
         * 表单数据验证失败
         */
        public static final String FORM_DATA_VALIDATION_FAILED = "FORM_DATA_VALIDATION_FAILED";

        /**
         * 流程定义已存在
         */
        public static final String PROCESS_DEFINITION_ALREADY_EXISTS = "PROCESS_DEFINITION_ALREADY_EXISTS";

        /**
         * 流程定义已发布
         */
        public static final String PROCESS_DEFINITION_ALREADY_DEPLOYED = "PROCESS_DEFINITION_ALREADY_DEPLOYED";

        /**
         * 流程定义已挂起
         */
        public static final String PROCESS_DEFINITION_SUSPENDED = "PROCESS_DEFINITION_SUSPENDED";

        /**
         * 流程实例已终止
         */
        public static final String PROCESS_INSTANCE_TERMINATED = "PROCESS_INSTANCE_TERMINATED";

        /**
         * 流程实例已挂起
         */
        public static final String PROCESS_INSTANCE_SUSPENDED = "PROCESS_INSTANCE_SUSPENDED";

        /**
         * 任务已完成
         */
        public static final String TASK_COMPLETED = "TASK_COMPLETED";

        /**
         * 任务已终止
         */
        public static final String TASK_TERMINATED = "TASK_TERMINATED";

        /**
         * 任务已挂起
         */
        public static final String TASK_SUSPENDED = "TASK_SUSPENDED";

        /**
         * 任务已锁定
         */
        public static final String TASK_LOCKED = "TASK_LOCKED";

        /**
         * 任务未锁定
         */
        public static final String TASK_NOT_LOCKED = "TASK_NOT_LOCKED";

        /**
         * 任务已认领
         */
        public static final String TASK_CLAIMED = "TASK_CLAIMED";

        /**
         * 任务未认领
         */
        public static final String TASK_NOT_CLAIMED = "TASK_NOT_CLAIMED";

        /**
         * 没有权限
         */
        public static final String NO_PERMISSION = "NO_PERMISSION";

        /**
         * 系统错误
         */
        public static final String SYSTEM_ERROR = "SYSTEM_ERROR";
    }
} 