/*
 * Copyright (C) 2007-2014 AcFun.com
 * All rights reserved.
 */
package com.gkframework.jms.rabbit.annotation;

import com.gkframework.jms.rabbit.api.MessageService;

import java.lang.annotation.*;

/**
 * 消息队列配置注解
 * <p>用于方法体
 *
 * @author moueimei
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Message {

    /**
     * 消息路由名称
     *
     * @return
     */
    String[] exchange() default {};

    /**
     * 消息队列名称
     *
     * @return
     */
    String[] queue() default "";

    /**
     * 路由关键字
     *
     * @return
     */
    String[] routeKeys() default "";

    /**
     * 消息接收模式
     *
     * @return
     */
    String type() default MessageService.TOPIC;

    boolean confirm() default false;
}
