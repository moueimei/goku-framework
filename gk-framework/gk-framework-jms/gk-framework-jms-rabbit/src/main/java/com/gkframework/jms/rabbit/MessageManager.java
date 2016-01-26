/*
 * Copyright (C) 2007-2014 AcFun.com
 * All rights reserved.
 */
package com.gkframework.jms.rabbit;

import com.gkframework.jms.rabbit.annotation.Message;
import com.gkframework.jms.rabbit.api.MessageService;
import com.gkframework.jms.rabbit.config.MessageConfig;
import com.google.gson.Gson;
import com.rabbitmq.client.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.expression.EvaluationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 消息管理器
 * <p>处理消息发送以及接收调用</p>
 *
 * @author moueimei
 */
@Service
public class MessageManager implements MessageService, ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageManager.class);

    private static final ConnectionFactory FACTORY = new ConnectionFactory();

    /**
     * 发送线程池,默认最大40线程
     */
    private static ExecutorService excS;
    /**
     * 接收线程池,按照listener数量创建
     */
    private static ExecutorService excL;

    private static Connection sendConnection;
    private static Connection recvConnection;

    @Value("${mq.host}")
    private String host;
    @Value("${mq.port}")
    private int port;
    @Value("${mq.user}")
    private String user;
    @Value("${mq.password}")
    private String password;
    @Value("${mq.reconnect}")
    private boolean reconnect;
    @Value("${mq.sendThreads}")
    private String num;

    /**
     * 检查接收连接是否正常
     *
     * @return
     */
    public static boolean isOpen() {
        if (recvConnection != null) {
            return recvConnection.isOpen();
        } else {
            return false;
        }
    }

    public static <T> String getMessage(T t) {
        String message;
        if (t instanceof String) {
            message = (String) t;
        } else if (t instanceof JSONObject) {
            message = ((JSONObject) t).toString();
        } else {
            Gson gson = new Gson();
            message = gson.toJson(t);
        }
        return message;
    }

    private static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (IOException ex) {
            LOGGER.error(null, ex);
        }
    }

    @Override
    public <T> void sendMessage(final T t) {
        //找到匹配的exchange,queue,bindkeys
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        StackTraceElement stack = stacks[2];
        final String className = stack.getClassName();
        final String methodName = stack.getMethodName();
        Message message = MessageConfig.get(className + "#" + methodName);
        excS.execute(new MessageSender<>(t, message, 0));
    }

    @Override
    public <T> void sendMessage(final T t, final int timeout) {
        //找到匹配的exchange,queue,bindkeys
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        StackTraceElement stack = stacks[2];
        final String className = stack.getClassName();
        final String methodName = stack.getMethodName();
        Message message = MessageConfig.get(className + "#" + methodName);
        excS.execute(new MessageSender<>(t, message, timeout));
    }

    /**
     * 处理消息接收
     */
    public void processMessage() {
        try {
            LOGGER.debug("Start listening");
            for (final String key : MessageConfig.getListeners()) {
                if (recvConnection == null || !recvConnection.isOpen()) {
                    LOGGER.warn("The mq server was disconnected.");
                    continue;
                }
                LOGGER.debug("Create channel");
                final Channel channel = recvConnection.createChannel();
                Message message = MessageConfig.get(key);
                String exchangeName = message.exchange()[0];
                String[] bindKeys = message.routeKeys();
                String queueName = message.queue()[0];
                String type = message.type();

                //设置队列信息
                channel.exchangeDeclare(exchangeName, type, true);
                queueName = declareQueue(channel, queueName, type);

                for (String bindKey : bindKeys) {
                    channel.queueBind(queueName, exchangeName, bindKey);
                }
                LOGGER.debug("Start new thread for listener :" + key);
                excL.submit(new MessageProcessor(channel, queueName, key, message.confirm()));
            }

        } catch (IOException ex) {
            LOGGER.error(null, ex);
        }
    }

    private String declareQueue(Channel channel, String queueName, String type) throws IOException {
        String finalQueueName = queueName;
        //广播模式下使用自动删除队列
        if (type.equals(FANOUT) && queueName.isEmpty()) {
            finalQueueName = channel.queueDeclare().getQueue();
        } else {
            try {
                finalQueueName = SpringUtil.parseSPEL(queueName, String.class);
            } catch (EvaluationException e) {
                LOGGER.warn(queueName);
            }
            channel.queueDeclare(finalQueueName, true, false, false, null);
        }
        return finalQueueName;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            LOGGER.debug("Triggered refresh event.");
            init();
        }
    }

    private void init() {
        initSenderPool();
        initListenerPool();
        //初始化mq连接
        FACTORY.setHost(this.host);
        FACTORY.setPort(this.port);
        FACTORY.setUsername(this.user);
        FACTORY.setPassword(this.password);
        FACTORY.setAutomaticRecoveryEnabled(this.reconnect);
        FACTORY.setTopologyRecoveryEnabled(this.reconnect);
        closeConnection(sendConnection);
        closeConnection(recvConnection);
        try {
            sendConnection = FACTORY.newConnection();
        } catch (IOException ex) {
            LOGGER.error(null, ex);
        }
        try {
            recvConnection = FACTORY.newConnection();
            recvConnection.addShutdownListener(new RecoverConsumerHandler());
        } catch (IOException e) {
            LOGGER.error("An unexpected error occurred.", e);
        }
        processMessage();
    }

    @PreDestroy
    public void destroy() {
        LOGGER.debug("Start destroying");
        if (excS != null) {
            excS.shutdownNow();
        }
        if (excL != null) {
            excL.shutdownNow();
        }
        closeConnection(sendConnection);
        closeConnection(recvConnection);
        LOGGER.debug("Destroy finished");
    }

    private void initSenderPool() {
        synchronized (MessageManager.class) {
            LOGGER.debug("Init sender thread pool.");
            if (excS != null && !excS.isShutdown()) {
                excS.shutdownNow();
            }
            int threadNum = 40;
            if (!StringUtils.isEmpty(num)) {
                threadNum = Integer.valueOf(num);
            }
            excS = Executors.newFixedThreadPool(threadNum, new ThreadFactory() {
                AtomicInteger i = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "Message sender - " + i.addAndGet(1));
                }
            });
        }
    }

    private void initListenerPool() {
        synchronized (MessageManager.class) {
            LOGGER.debug("Init listener thread pool.");
            if (excL != null && !excL.isShutdown()) {
                excL.shutdownNow();
            }
            int n = 10;
            if (!MessageConfig.isListenerEmpty()) {
                n = MessageConfig.getListenerSize();
            }
            excL = Executors.newFixedThreadPool(n, new ThreadFactory() {
                AtomicInteger i = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "Message receiver - " + i.addAndGet(1));
                }
            });
        }
    }

    /**
     * 重启接收端线程池
     */
    public void restartListenerPool() {
        LOGGER.debug("Shutdown listener thread pool");
        excL.shutdownNow();
        initListenerPool();
    }

    static class MessageSender<T> extends Thread {

        private Message annotation;
        private T t;
        private int timeout;

        public MessageSender(T t, Message annotation, int timeout) {
            this.t = t;
            this.annotation = annotation;
            this.timeout = timeout;
        }

        @Override
        public void run() {
            LOGGER.debug("Start sending");
            //将POJO序列化
            String message = getMessage(t);
            LOGGER.debug("Message:" + message);
            //连接mq并发送
            try {
                if (sendConnection == null) {
                    LOGGER.warn("The send connection was not created.");
                    return;
                }
                LOGGER.debug("Create channel");
                Channel channel = sendConnection.createChannel();
                channel.exchangeDeclare(annotation.exchange()[0], annotation.type(), true);
                AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder()
                        .contentType("text/plain")
                        .contentEncoding(ENCODING)
                        .deliveryMode(2)
                        .priority(0);
                if (timeout > 0) {
                    builder.expiration(String.valueOf(timeout));
                }
                AMQP.BasicProperties properties = builder.build();
                channel.basicPublish(annotation.exchange()[0], annotation.routeKeys()[0], properties, message.getBytes(ENCODING));
                channel.close();

            } catch (IOException ex) {
                LOGGER.error(null, ex);
            } catch (ShutdownSignalException ex) {
                LOGGER.error("The mq server was disconnected.", ex);
            }
        }

    }

}