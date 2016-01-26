/*
 * Copyright (C) 2007-2014 AcFun.com
 * All rights reserved.
 */
package com.gkframework.jms.rabbit;

import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author moueimei
 */
public class RecoverConsumerHandler implements ShutdownListener {

    private static final ExecutorService RECOVERY_EXECUTORS = Executors.newCachedThreadPool(new ThreadFactory() {
        AtomicInteger i = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Message recover thread - " + i.addAndGet(1));
        }
    });

    private static final Logger LOGGER = LoggerFactory.getLogger(RecoverConsumerHandler.class);

    @Override
    public void shutdownCompleted(final ShutdownSignalException e) {
        if (e.isHardError()) {
            if (!e.isInitiatedByApplication()) {
                LOGGER.error("Connection was closed unexpectedly.", e);
                LOGGER.error("Cause:" + e.getReason().toString());
                RECOVERY_EXECUTORS.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            recoverConsumer();
                            LOGGER.debug("All the consumers was recovered.");
                        } catch (Exception e) {
                            LOGGER.error("Failed to recover connection ", e);
                        }
                    }
                });
            }
        } else {
            LOGGER.error("Channel was closed unexpectedly.", e);
            // channel的异常关闭目前不进行处理
        }
    }

    private void recoverConsumer() {
        while (true) {
            if (MessageManager.isOpen()) {
                LOGGER.warn("Reconnected to the mq server.");
                MessageManager manager = SpringUtil.getBean(MessageManager.class);
                manager.restartListenerPool();
                manager.processMessage();
                break;
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                LOGGER.error(null, ex);
            }
        }
    }
}
