package com.gkframework.qos.server.monitor.service;

import com.gkframework.qos.server.core.entity.MinStat;

public interface MinStatService {
	
	public MinStat getByMd5(String md5);

	public void save(MinStat minStat);
	
	public void update(MinStat minStat);
	
}
