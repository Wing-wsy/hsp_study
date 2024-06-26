package com.bfxy.rabbit.common.convert;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import com.google.common.base.Preconditions;

/**
 * 消息转换GenericMessageConverter 类的代理（代理模式或者装饰者模式）
 */
public class RabbitMessageConverter implements MessageConverter {

	// 被代理的对象
	private GenericMessageConverter delegate;

	// 默认过期时间
    private final String delaultExprie = String.valueOf(24 * 60 * 60 * 1000);
	
	public RabbitMessageConverter(GenericMessageConverter genericMessageConverter) {
		Preconditions.checkNotNull(genericMessageConverter);
		this.delegate = genericMessageConverter;
	}
	
	@Override
	public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        // messageProperties.setExpiration(delaultExprie);
		com.bfxy.rabbit.api.Message message = (com.bfxy.rabbit.api.Message)object;
		messageProperties.setDelay(message.getDelayMills());
		return this.delegate.toMessage(object, messageProperties);
	}

	@Override
	public Object fromMessage(Message message) throws MessageConversionException {
		com.bfxy.rabbit.api.Message msg = (com.bfxy.rabbit.api.Message) this.delegate.fromMessage(message);
		return msg;
	}

}
