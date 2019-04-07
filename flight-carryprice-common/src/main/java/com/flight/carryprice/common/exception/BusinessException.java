package com.flight.carryprice.common.exception;

/**
 * 业务异常
 * @author lgp
 * @date 2016年9月2日 下午4:10:36
 */
public class BusinessException extends SystemException {

	private static final long serialVersionUID = 4458353756296440746L;
	
	public BusinessException() {
		super();
	}

	public BusinessException(String errorCode, String message, Object data) {
		super(errorCode, message, data);
	}

	public BusinessException(String errorCode, String message) {
		super(errorCode, message);
	}

	public BusinessException(String message) {
		super(message);
	}

}
