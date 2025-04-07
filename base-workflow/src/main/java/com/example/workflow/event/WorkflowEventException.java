package com.example.workflow.event;

/**
 * 工作流事件异常类
 *
 * @author Author
 * @version 1.0
 */
public class WorkflowEventException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private String code;

    /**
     * 构造函数
     *
     * @param message 错误信息
     */
    public WorkflowEventException(String message) {
        super(message);
    }

    /**
     * 构造函数
     *
     * @param code    错误码
     * @param message 错误信息
     */
    public WorkflowEventException(String code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造函数
     *
     * @param message 错误信息
     * @param cause   异常原因
     */
    public WorkflowEventException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造函数
     *
     * @param code    错误码
     * @param message 错误信息
     * @param cause   异常原因
     */
    public WorkflowEventException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置错误码
     *
     * @param code 错误码
     */
    public void setCode(String code) {
        this.code = code;
    }
} 