package com.gkframework.model.response;

/**
 * 错误常量
 * User: wuqingming
 * Date: 13-12-7
 * Time: 上午10:35
 */
public enum ErrorConstants {
    /**
     * 大于等于100，小于200的错误为业务错误
     */
    RECORD_NOT_EXISTS(100, "%s不存在"),
    RECORD_UNAVAILABLE(101, "%s不可用"),
    DUPLICATE_RECORD(102, "重复记录"),
    REFERENCED_RECORD(103, "记录被引用"),
    REQ_PARAM(104, "请求参数错误"),

    GENERATE_COUNT_EXCEED_MAX_LIMIT(126, "生成数超过最大限制"),
    ILLEGAL_OP(127, "非法操作"),

    /**
     * 大于等于200的错误为系统错误
     */
    SYS_EXCEPT(201, "系统错误"),
    UNKNOWN(202, "未知错误"),
    OK(0,"成功");



    private Integer code;
    private String msg;

    ErrorConstants(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
