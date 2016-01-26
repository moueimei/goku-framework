/*
 * Copyright (C) 2007-2014 AcFun.com
 * All rights reserved.
 */

package com.gkframework.jms.rabbit.api;

/**
 * 消息发送接口
 *
 * @author moueimei
 */
public interface MessageService {

    /**
     * 路由模式
     */
    public static final String TOPIC = "topic";

    /**
     * 广播模式
     */
    public static final String FANOUT = "fanout";

    /**
     * 编码格式
     */
    public static final String ENCODING = "UTF-8";

    /**
     * 发送消息
     *
     * @param <T>
     * @param t   任意实体
     */
    public <T> void sendMessage(T t);

    /**
     * 发送消息
     *
     * @param <T>
     * @param t       任意实体
     * @param timeout 过期时间(单位毫秒)
     */
    public <T> void sendMessage(T t, int timeout);

}
