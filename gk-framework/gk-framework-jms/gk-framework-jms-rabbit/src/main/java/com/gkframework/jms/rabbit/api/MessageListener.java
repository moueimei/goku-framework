/*
 * Copyright (C) 2007-2014 AcFun.com
 * All rights reserved.
 */

package com.gkframework.jms.rabbit.api;

/**
 * 消息监听接口
 *
 * @author moueimei
 */
public interface MessageListener {

    /**
     * 监听
     *
     * @param json json字符串
     */
    public void listen(String json);
}
