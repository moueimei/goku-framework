package com.gkframework.qos.server.monitor.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.gkframework.qos.server.core.entity.AppInfo;
import com.gkframework.qos.server.monitor.dao.AppInfoDao;
import com.gkframework.qos.server.monitor.service.AppInfoService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

@Service("appInfoService")
public class AppInfoServiceImpl implements AppInfoService {

	private final ConcurrentMap<String, AtomicReference<AppInfo>> appInfoMap
			= new ConcurrentHashMap<String, AtomicReference<AppInfo>>();

	@Resource
	private AppInfoDao appInfoDao;

	public void setAppInfoDao(AppInfoDao appInfoDao) {
		this.appInfoDao = appInfoDao;
	}

	public AppInfo getByCode(String code) {
		return appInfoDao.getByCode(code);
	}

	public void insert(AppInfo appInfo) {
			appInfoDao.insert(appInfo);
	}

	public Long genByCode(String code) {
		code = StringUtils.isNotEmpty(code)?code.trim():"";
		AtomicReference<AppInfo> reference = appInfoMap.get(code);
		if (reference == null) {
			appInfoMap.putIfAbsent(code, new AtomicReference<AppInfo>());
			reference = appInfoMap.get(code);
		}

		AppInfo current = reference.get();
		AppInfo update = null;

		if (null == current) {
			update = this.getByCode(code);
			if (null != update) {
				reference.compareAndSet(current, update);
				return update.getId();
			} else {
				update = new AppInfo();
				update.setCode(code);
				update.setName(code);
				update.setDisorder(100);
				update.setState((short)1);
				update.setCreateTime(new Date());

				this.insert(update);
				reference.compareAndSet(current, update);
				return update.getId();
			}
		} else {
			return current.getId();
		}

	}

	@PostConstruct
	public void createTable() {
		appInfoDao.createTable();
	}
	
}
