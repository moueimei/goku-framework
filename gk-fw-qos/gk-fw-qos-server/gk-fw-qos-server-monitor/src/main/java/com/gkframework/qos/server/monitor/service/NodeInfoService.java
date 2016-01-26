package com.gkframework.qos.server.monitor.service;

import com.gkframework.qos.server.core.entity.NodeInfo;

public interface NodeInfoService {

	public NodeInfo getByIp(String ip);

	public Long genByIp(String ip);
	
	public void createTable();
}
