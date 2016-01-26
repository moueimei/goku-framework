package com.gkframework.qos.collector.core.utils;


import com.gkframework.config.core.PropertyConfigurer;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ApplicationUtils {

	public static String APPLICATION_NAME_KEY = "application.name";

	public static String getApplicationName() {
		String name = PropertyConfigurer.getString(APPLICATION_NAME_KEY);
		if (null == name) {
			name = SystemPropertiesUtils.getString(APPLICATION_NAME_KEY);
			if (null == name) {
				try {
					return InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					return null;
				}
			} else {
				return name;
			}
		} else {
			return name;
		}
	}

}