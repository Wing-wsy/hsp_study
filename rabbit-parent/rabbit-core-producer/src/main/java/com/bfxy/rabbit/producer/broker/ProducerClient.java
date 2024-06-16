package com.bfxy.rabbit.producer.broker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bfxy.rabbit.api.Message;
import com.bfxy.rabbit.api.MessageProducer;
import com.bfxy.rabbit.api.MessageType;
import com.bfxy.rabbit.api.SendCallback;
import com.bfxy.rabbit.api.exception.MessageRunTimeException;
import com.google.common.base.Preconditions;

/**
 *  发送消息的实际实现类
 */
@Component
public class ProducerClient implements MessageProducer {

	@Autowired
	private RabbitBroker rabbitBroker;
	
	@Override
	public void send(Message message) throws MessageRunTimeException {
		Preconditions.checkNotNull(message.getTopic());
		String messageType = message.getMessageType();
		switch (messageType) {
			// 迅速消息：不需要保障消息的可靠性, 也不需要做confirm确认
			case MessageType.RAPID:
				rabbitBroker.rapidSend(message);
				break;
			// 确认消息：不需要保障消息的可靠性，但是会做消息的confirm确认
			case MessageType.CONFIRM:
				rabbitBroker.confirmSend(message);
				break;
			// 可靠性消息： 一定要保障消息的100%可靠性投递，不允许有任何消息的丢失
			case MessageType.RELIANT:
				rabbitBroker.reliantSend(message);
				break;
		default:
			break;
		}
	}

	/**
	 * 	$send Messagetype
	 */
	@Override
	public void send(List<Message> messages) throws MessageRunTimeException {
		messages.forEach( message -> {
			message.setMessageType(MessageType.RAPID);
			MessageHolder.add(message);
		});
		rabbitBroker.sendMessages();
	}
	
	@Override
	public void send(Message message, SendCallback sendCallback) throws MessageRunTimeException {
		// TODO Auto-generated method stub
		
	}

}
