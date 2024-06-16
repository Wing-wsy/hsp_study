package com.bfxy.rabbit.common.convert;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import com.bfxy.rabbit.common.serializer.Serializer;
import com.google.common.base.Preconditions;

/**
 * 	消息转换类
 */
public class GenericMessageConverter implements MessageConverter {
	
	private Serializer serializer;

	public GenericMessageConverter(Serializer serializer) {
		Preconditions.checkNotNull(serializer);
		this.serializer = serializer;
	}

	/** 【收到消息】将amqp的message 转成 自己的 com.bfxy.rabbit.api.Message 【mq传输消息只认amqp的message】*/
	@Override
	public Object fromMessage(org.springframework.amqp.core.Message message) throws MessageConversionException {
		return this.serializer.deserialize(message.getBody());
	}

	/** 【发送消息】将自己的 com.bfxy.rabbit.api.Message 转成 amqp的message 【mq传输消息只认amqp的message】*/
	@Override
	public org.springframework.amqp.core.Message toMessage(Object object, MessageProperties messageProperties)
			throws MessageConversionException {
		return new org.springframework.amqp.core.Message(this.serializer.serializeRaw(object), messageProperties);
	}

}
