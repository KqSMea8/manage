package com.flight.carryprice.common.exception;

/**
 * 系统异常
 * 
 * @author lgp
 * @date 2016年9月2日 下午4:09:22
 */
public class SystemException extends RuntimeException {

	private static final long serialVersionUID = -2085452067731288589L;

	private String errorCode;

	private Object data;

	public SystemException(String errorCode, String message, Object data) {
		super(message);
		this.errorCode = errorCode;
		this.data = data;
	}

	public SystemException(String errorCode, String message) {
		this(errorCode, message, null);
	}

	public SystemException(String message) {
		super(message);
	}

	public SystemException() {
		super();
	}

	// get,set

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
