/**
 *
 */
package com.gkframework.model.response;

import java.io.Serializable;

/**
 *
 */
public  class ServiceStatus implements Serializable {

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String WARNING = "warning";

    /**
     * 结果标识
     */
    private String result = null;
     /**
     * 错误代码标识
     */
    private Integer errorCode = null;
    /**
     * 详细信息
     */
    private String message = null;

    /**
     *
     */
    public ServiceStatus() {

    }

    public ServiceStatus(int code) {
        super();
        this.errorCode = code;
    }

    public ServiceStatus(String result) {
        super();
        this.result = result;
    }

    public ServiceStatus(String result, String message) {
        super();
        this.result = result;
        this.message = message;
    }

    public ServiceStatus(String result, Integer errorCode) {
        super();
        this.result = result;
        this.errorCode = errorCode;
    }

    public ServiceStatus(String result, ErrorConstants error) {
        super();
        this.result = result;
        this.errorCode = error.getCode();
        this.message = error.getMsg();
    }

    public ServiceStatus(String result, Integer errorCode, String message) {
        super();
        this.result = result;
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    /**
     * @param result
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * @return the message
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("ServiceStatus [result=%s, errorCode=%s, message=%s]", result, errorCode, message);
    }

}
