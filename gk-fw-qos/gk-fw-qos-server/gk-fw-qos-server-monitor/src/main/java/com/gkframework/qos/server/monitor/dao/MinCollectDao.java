package com.gkframework.qos.server.monitor.dao;

import org.springframework.stereotype.Repository;
import com.gkframework.qos.server.core.entity.MinCollect;

@Repository
public interface MinCollectDao {
	
	public void insert(MinCollect sc);
    
	public void createTable();
}