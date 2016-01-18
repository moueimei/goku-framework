package com.gkframework.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 调度任务抽象模板类
 *
 * Created by user on 15/11/26.
 */
public abstract class AbsQuartz {
    /* 执行成功标识 */
    public static final Integer EXE_SUCCESS = 1;
    /* 执行失败标识 */
    public static final Integer EXE_FAILED = -1;
    /* 未执行标识 */
    public static final Integer UN_EXE = 0;

    /* 核心执行方法 */
    public abstract Integer execute();

    /* 获取当前调度任务的名称 */
    public abstract String getName();


    /**
     * 调度任务执行的相关参数验证
     * @return
     */
    public boolean validate() {
        return true;
    }

    /* 失败日志 */
    public String getFailedNotifyContent(){
        String NULL_SPACE = " ";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        StringBuffer sb = new StringBuffer();
        sb.append(sf.format(new Date()));
        sb.append(NULL_SPACE);
        sb.append(getName());
        sb.append(NULL_SPACE);
        sb.append("execute occurs exception ");
        return sb.toString();
    }

    /* 通知邮件地址 */
    public abstract String getNoticeEmail();
}
