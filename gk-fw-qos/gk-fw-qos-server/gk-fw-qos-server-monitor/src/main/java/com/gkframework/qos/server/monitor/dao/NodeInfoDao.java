package com.gkframework.qos.server.monitor.dao;


import org.springframework.stereotype.Repository;
import com.gkframework.qos.server.core.entity.NodeInfo;

@Repository
public interface NodeInfoDao {
	
	public NodeInfo getByIp(String ip);
	
	public void insert(NodeInfo nodeInfo);
	
	public void createTable();
}