package com.gkframework.model;

public enum OperatorType {

	SYSTEM("系统自动", (short)3),
	OSS("运营后台", (short)2),
	USER("用户", (short)1);
	
	// 成员变量
	private String name;
	private short index;

	// 构造方法
	private OperatorType(String name, short index) {
		this.name = name;
		this.index = index;
	}
	
	// 普通方法
	public static String getName(int index) {
		for (OperatorType c : OperatorType.values()) {
			if (c.getIndex() == index) {
				return c.name;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getIndex() {
		return index;
	}

	public void setIndex(short index) {
		this.index = index;
	}
	
}
