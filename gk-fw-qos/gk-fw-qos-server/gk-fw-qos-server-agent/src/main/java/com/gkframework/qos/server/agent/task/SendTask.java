package com.gkframework.qos.server.agent.task;


import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.gkframework.qos.collector.core.utils.DateFormatUtils;
import com.gkframework.qos.server.agent.AgentMonitor;

import javax.annotation.Resource;
import java.util.Date;

@Component("sendTask")
@Slf4j
public class SendTask {
	private static Logger logger = Logger.getLogger(SendTask.class);

	@Resource
	private AgentMonitor agentMonitor;

	@Scheduled(cron = "0 0/1 * * * ?")
	public void task() {
		logger.info("--->send task time: " +
				DateFormatUtils.toString(new Date(), DateFormatUtils.pattern19));
		
		//发送数据
		agentMonitor.send();
		
		//log.info("--->total count =: " + MonitorSendService.count);
	}

}