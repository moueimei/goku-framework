package com.gkframework.jms.rabbit;

import com.gkframework.jms.rabbit.api.MessageListener;
import com.gkframework.jms.rabbit.api.MessageService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * 消息处理线程
 * Created on 2014/8/15.
 *
 * @author moueimei
 */
class MessageProcessor extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProcessor.class);

    private final Channel channel;
    private final String queueName;
    private final String key;
    private final boolean confirm;

    MessageProcessor(Channel channel, String queueName, String key, boolean confirm) {
        this.channel = channel;
        this.queueName = queueName;
        this.key = key;
        this.confirm = confirm;
    }

    @Override
    public void run() {
        try {
            Thread.currentThread().setName("Message receiver - " + key + " thread");
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(queueName, consumer);
            LOGGER.debug("Get consumer");
            MessageListener listener = SpringUtil.getBean(getClassName(key), MessageListener.class);
            LOGGER.debug("Get bean : " + key);
            for (; ; ) {
                LOGGER.debug("Delivery message,wating...");
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody(), MessageService.ENCODING);
                String routingKey = delivery.getEnvelope().getRoutingKey();
                //发送反馈
                if (!confirm) {
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
                LOGGER.debug(" [x] Received '" + routingKey + "':'" + message + "'");
                resolveMessage(listener, message, confirm, channel, delivery);
            }
        } catch (IOException ex) {
            LOGGER.error(null, ex);
        } catch (InterruptedException ex) {
            LOGGER.error("Thread interrupted.", ex);
        } catch (ShutdownSignalException ex) {
            LOGGER.error("Connection shutdown.", ex);
        } catch (ConsumerCancelledException ex) {
            LOGGER.error("Consumer cancelled.", ex);
        }
    }

    private void resolveMessage(MessageListener listener, String message, boolean confirm, Channel channel, QueueingConsumer.Delivery delivery) {
        try {
            //调用监听程序
            if ("".equals(message)) {
                LOGGER.warn("Received empty message.");
                return;
            }
            listener.listen(message);
            if (confirm) {
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        } catch (Exception ex) {
            LOGGER.error(null, ex);
            if (confirm) {
                try {
                    channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
                } catch (IOException e) {
                    LOGGER.error("An unexpected error occurred when basicNack.", e);
                }
            }
        }
    }

    private String getClassName(String fullName) {
        String name = fullName.split("#")[0];
        String[] names = name.split("\\.");
        name = names[names.length - 1];
        return StringUtils.uncapitalize(name);
    }
}
