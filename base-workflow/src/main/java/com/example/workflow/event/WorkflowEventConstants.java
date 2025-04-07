package com.example.workflow.event;

/**
 * 工作流事件常量类
 *
 * @author Author
 * @version 1.0
 */
public class WorkflowEventConstants {

    /**
     * 事件类型相关常量
     */
    public static class EventType {
        /**
         * 流程定义相关事件
         */
        public static class ProcessDefinition {
            /**
             * 部署
             */
            public static final String DEPLOY = "PROCESS_DEFINITION_DEPLOY";

            /**
             * 删除
             */
            public static final String DELETE = "PROCESS_DEFINITION_DELETE";

            /**
             * 激活
             */
            public static final String ACTIVATE = "PROCESS_DEFINITION_ACTIVATE";

            /**
             * 挂起
             */
            public static final String SUSPEND = "PROCESS_DEFINITION_SUSPEND";
        }

        /**
         * 流程实例相关事件
         */
        public static class ProcessInstance {
            /**
             * 启动
             */
            public static final String START = "PROCESS_INSTANCE_START";

            /**
             * 完成
             */
            public static final String COMPLETE = "PROCESS_INSTANCE_COMPLETE";

            /**
             * 终止
             */
            public static final String TERMINATE = "PROCESS_INSTANCE_TERMINATE";

            /**
             * 删除
             */
            public static final String DELETE = "PROCESS_INSTANCE_DELETE";

            /**
             * 激活
             */
            public static final String ACTIVATE = "PROCESS_INSTANCE_ACTIVATE";

            /**
             * 挂起
             */
            public static final String SUSPEND = "PROCESS_INSTANCE_SUSPEND";

            /**
             * 变量更新
             */
            public static final String VARIABLE_UPDATE = "PROCESS_INSTANCE_VARIABLE_UPDATE";
        }

        /**
         * 任务相关事件
         */
        public static class Task {
            /**
             * 创建
             */
            public static final String CREATE = "TASK_CREATE";

            /**
             * 分配
             */
            public static final String ASSIGN = "TASK_ASSIGN";

            /**
             * 完成
             */
            public static final String COMPLETE = "TASK_COMPLETE";

            /**
             * 删除
             */
            public static final String DELETE = "TASK_DELETE";

            /**
             * 认领
             */
            public static final String CLAIM = "TASK_CLAIM";

            /**
             * 取消认领
             */
            public static final String UNCLAIM = "TASK_UNCLAIM";

            /**
             * 委派
             */
            public static final String DELEGATE = "TASK_DELEGATE";

            /**
             * 转办
             */
            public static final String TRANSFER = "TASK_TRANSFER";

            /**
             * 添加候选人
             */
            public static final String ADD_CANDIDATE_USER = "TASK_ADD_CANDIDATE_USER";

            /**
             * 删除候选人
             */
            public static final String DELETE_CANDIDATE_USER = "TASK_DELETE_CANDIDATE_USER";

            /**
             * 添加候选组
             */
            public static final String ADD_CANDIDATE_GROUP = "TASK_ADD_CANDIDATE_GROUP";

            /**
             * 删除候选组
             */
            public static final String DELETE_CANDIDATE_GROUP = "TASK_DELETE_CANDIDATE_GROUP";

            /**
             * 变量更新
             */
            public static final String VARIABLE_UPDATE = "TASK_VARIABLE_UPDATE";
        }

        /**
         * 表单相关事件
         */
        public static class Form {
            /**
             * 创建
             */
            public static final String CREATE = "FORM_CREATE";

            /**
             * 更新
             */
            public static final String UPDATE = "FORM_UPDATE";

            /**
             * 删除
             */
            public static final String DELETE = "FORM_DELETE";

            /**
             * 发布
             */
            public static final String PUBLISH = "FORM_PUBLISH";

            /**
             * 禁用
             */
            public static final String DISABLE = "FORM_DISABLE";

            /**
             * 数据保存
             */
            public static final String DATA_SAVE = "FORM_DATA_SAVE";

            /**
             * 数据更新
             */
            public static final String DATA_UPDATE = "FORM_DATA_UPDATE";

            /**
             * 数据删除
             */
            public static final String DATA_DELETE = "FORM_DATA_DELETE";
        }
    }

    /**
     * 错误码相关常量
     */
    public static class ErrorCode {
        /**
         * 事件处理失败
         */
        public static final String EVENT_HANDLE_FAILED = "EVENT_HANDLE_FAILED";

        /**
         * 事件发布失败
         */
        public static final String EVENT_PUBLISH_FAILED = "EVENT_PUBLISH_FAILED";

        /**
         * 事件类型无效
         */
        public static final String INVALID_EVENT_TYPE = "INVALID_EVENT_TYPE";

        /**
         * 事件数据无效
         */
        public static final String INVALID_EVENT_DATA = "INVALID_EVENT_DATA";
    }
} 