package com.gkframework.qos.server.monitor.service;

import com.gkframework.qos.server.monitor.model.Collect;

public interface MinCollectService {

	public void save(Collect collect);
	
	public void createTable();
}
