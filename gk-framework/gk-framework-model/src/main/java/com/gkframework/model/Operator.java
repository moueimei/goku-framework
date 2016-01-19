package com.gkframework.model;

import java.io.Serializable;

public class Operator implements Serializable {

	private static final long serialVersionUID = -3380428902609264726L;

	protected Object user; // 操作者用户名称或者ID

	protected OperatorType type; // 操作者人
	
	public Operator() {}
	
	public Operator(OperatorType type, Object user) {
		this.type = type;
		this.user = user;
	}
	
	public static Operator creat(OperatorType type, Object user) {
		return new Operator(type, user);
	}

	public Object getUser() {
		return user;
	}

	public void setUser(Object user) {
		this.user = user;
	}

	public OperatorType getType() {
		return type;
	}

	public void setType(OperatorType type) {
		this.type = type;
	}
	
    public Integer getUserAsInteger() {
    	return (Integer)user;
    }
    
    public Long getUserAsLong() {
    	return (Long)user;
    }
    
    public String getUserAsString() {
    	return user.toString();
    }

}
