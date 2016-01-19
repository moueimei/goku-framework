package com.gkframework.exception;

/**
 * Base class for all custom exception thrown in AppleFramework
 * 
 * @author moueimei
 * @date: 2012-10-15
 * 
 */
@SuppressWarnings("rawtypes")
public class ServiceException extends GkException {

	private static final long serialVersionUID = 7696865849245536841L;

	public static final String RSP = "rsp.";

	public static final String ERROR = "-error:";

	private String clazz;

	private Object[] params;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ServiceException() {
		super();
	}

	public ServiceException(String code) {
		this.code = code;
	}

	public ServiceException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public ServiceException(String code, Throwable throwable) {
		super(throwable);
		super.code = code;
	}

	public ServiceException(Class clazz, String code) {
		super.code = code;
		this.clazz = getInterfaceName(clazz);
	}

	public ServiceException(Class clazz, String code, Object... params) {
		super.code = code;
		this.clazz = getInterfaceName(clazz);
		this.params = params;
	}

	public ServiceException(Class clazz, String code, Throwable throwable) {
		super(code, throwable);
		super.code = code;
	}

	public ServiceException(Class clazz, String code, Throwable throwable, Object... params) {
		super(code, throwable);
		this.code = code;
	}

	public String getKey() {
		if(null == clazz)
			return RSP + "." + code;
		else
			return RSP + transform(clazz) + ERROR + code;
	}

	public String getMessage() {
		return message;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	/**
	 * 对服务名进行标准化处理：如book.upload转换为book-upload，
	 *
	 * @param className
	 * @return
	 */
	public String transform(String className) {
		if (className != null) {
			className = className.replace(".", "-");
			return className;
		} else {
			return "LACK_INTEFACE";
		}
	}

	public String getInterfaceName(Class clazz) {
		Class[] clazzs = clazz.getInterfaces();
		if(clazzs.length > 0) {
			return clazzs[0].getName();
		}
		else {
			return clazz.getName();
		}
	}


}
