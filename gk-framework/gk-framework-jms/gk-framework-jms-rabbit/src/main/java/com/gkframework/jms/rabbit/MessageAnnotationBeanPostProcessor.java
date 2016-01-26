/*
 * Copyright (C) 2007-2014 AcFun.com
 * All rights reserved.
 */
package com.gkframework.jms.rabbit;

import com.gkframework.jms.rabbit.annotation.Message;
import com.gkframework.jms.rabbit.api.MessageListener;
import com.gkframework.jms.rabbit.config.MessageConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 扫描包读取所有的消息发送配置
 *
 * @author moueimei
 * @see com.gkframework.jms.rabbit.MessageManager
 */
@Component
public class MessageAnnotationBeanPostProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageAnnotationBeanPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Class<?> clz = bean.getClass();
        Class<Message> annClz = Message.class;
        String clzName = clz.getName();
        Method[] methods = clz.getMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(annClz)) {
                continue;
            }
            Message message = method.getAnnotation(annClz);
            String key = clzName + "#" + method.getName();
            MessageConfig.putMessageMap(key, message);
            LOGGER.debug("discover : " + key);
        }
        if (bean instanceof MessageListener) {
            MessageConfig.addListener(clzName + "#listen");
            LOGGER.debug("add listener : " + clzName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }

}
