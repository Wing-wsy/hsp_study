package com.bfxy.rabbit.api.exception;

/**
 *  自定义异常
 */
public class MessageException extends Exception {

	private static final long serialVersionUID = 6347951066190728758L;

	public MessageException() {
		super();
	}
	
	public MessageException(String message) {
		super(message);
	}
	
	public MessageException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MessageException(Throwable cause) {
		super(cause);
	}
	
}
