/*
 * Copyright (C) 2007-2014 AcFun.com
 * All rights reserved.
 */
package com.gkframework.jms.rabbit.test;

import com.gkframework.jms.rabbit.SpringUtil;
import com.gkframework.jms.rabbit.annotation.Message;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author moueimei
 */
class SendAdvice implements MethodInterceptor {

    private static final ConnectionFactory FACTORY = new ConnectionFactory();
    private static final Logger LOGGER = LoggerFactory.getLogger(SendAdvice.class);
    private static boolean init = false;
    private final String json;

    SendAdvice(String json) {
        this.json = json;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Message message = method.getAnnotation(Message.class);
        String[] exchange = message.exchange();
        String[] routeKeys = message.routeKeys();
        init();
        LOGGER.debug("declare queue");
        Connection con = FACTORY.newConnection();
        Channel ch = con.createChannel();

        try {
            ch.exchangeDeclare(exchange[0], "topic", true);
            String queueName = ch.queueDeclare().getQueue();
            for (String key : routeKeys) {
                ch.queueBind(queueName, exchange[0], key);
            }

            Object result = invocation.proceed();

            Thread.sleep(5000);
            LOGGER.debug("deliver message");
            //取出消息
            QueueingConsumer consumer = new QueueingConsumer(ch);
            ch.basicConsume(queueName, consumer);
            QueueingConsumer.Delivery delivery = consumer.nextDelivery(1000);
            if (delivery == null) {
                throw new IllegalArgumentException("Can't receive any message.");
            }
            ch.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            String str = new String(delivery.getBody(), "UTF-8");

            LOGGER.info(str);
            LOGGER.debug("verfiy result");
            JSONAssert.assertEquals(json, str, false);
            return result;
        } finally {
            ch.exchangeDelete(exchange[0]);
            ch.close();
            con.close();
        }

    }

    private void init() {
        if (init) {
            return;
        }
        FACTORY.setHost(SpringUtil.getProperty("mq.host"));
        FACTORY.setPort(Integer.valueOf(SpringUtil.getProperty("mq.port")));
        FACTORY.setUsername(SpringUtil.getProperty("mq.user"));
        FACTORY.setPassword(SpringUtil.getProperty("mq.password"));
        init = true;
    }

}
