package com.gkframework.qos.server.agent;

import com.gkframework.qos.collector.core.URL;
import com.gkframework.qos.server.agent.utils.ByteUtils;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

/**
 * 监控发送
 */
public class MonitorSendService {
	
	private Producer<String, byte[]> producer;

	private String topic;

	public void setProducer(Producer<String, byte[]> producer) {
		this.producer = producer;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void send(URL url) {
		byte[] dataBytes = ByteUtils.toBytes(url);
		KeyedMessage<String, byte[]> producerData = new KeyedMessage<String, byte[]>(
				topic, dataBytes);
		producer.send(producerData);
	}

	public void destroy() {
		if (null != producer)
			producer.close();
	}
}
