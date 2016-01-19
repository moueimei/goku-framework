package com.gkframework.model.response;

/**
 * 服务请求响应消息
 * @param <T>
 */
public class ServiceResponse<T> extends BaseResponse {

    /**
     * 被操作的记录信息
     */
    private T info;

    /**
     * code
     * @param code
     */
    public ServiceResponse(int code) {
        super.setCode(code);
    }

    /**
     *
     * @param result 信息
     * @param code
     */
    public ServiceResponse(String result, int code) {
        super.setMessage(result);
        super.setCode(code);
    }

    /**
     *
     * @param result 信息
     * @param code
     */
    public ServiceResponse(String result, int code, T info) {
        super.setMessage(result);
        super.setCode(code);
        this.setInfo(info);
    }

    public static ServiceResponse success() {
        return new ServiceResponse(ServiceStatus.SUCCESS,ErrorConstants.OK.getCode());
    }

    public static ServiceResponse failure() {
        return new ServiceResponse(FAILURE,ErrorConstants.SYS_EXCEPT.getCode());
    }




    /**
     * 设置返回对象
     * @param info
     * @return
     */
    public ServiceResponse setInfo(T info) {
        this.info = info;
        return this;
    }

    public BaseResponse setCode(int code) {
        super.setCode(code);
        return this;
    }

    public int getCode() {
        return super.getCode();
    }

    public String getMessage() {
        return super.getMessage();
    }

    public T getInfo() {
        return info;
    }

}
