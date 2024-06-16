package com.bfxy.rabbit.producer.constant;

/**
 * 	消息的发送状态
 */
public enum BrokerMessageStatus {

	// 消息发送，待MQ确认
	SENDING("0"),
	// 消息发送，MQ返回ACK
	SEND_OK("1"),
	// 消息发送失败
	SEND_FAIL("2"),
	SEND_FAIL_A_MOMENT("3");
	
	private String code;
	
	private BrokerMessageStatus(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	
}
