package com.gkframework.model.response;

import java.io.Serializable;

/** 
 * @Description:base response
 * @version 1.0
 */
public class BaseResponse implements Serializable {

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String WARNING = "warning";

    /**
     * 返回值
     */
    private int code;
    /**
     * 返回信息
     */
    private String message;

    public int getCode() {
        return code;
    }

    public BaseResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }
    
    public BaseResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
