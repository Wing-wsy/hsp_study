package com.bfxy.rabbit.common.serializer.impl;

import com.bfxy.rabbit.api.Message;
import com.bfxy.rabbit.common.serializer.Serializer;
import com.bfxy.rabbit.common.serializer.SerializerFactory;

/**
 * 	序列化和反序列化工厂接口实现
 */
public class JacksonSerializerFactory implements SerializerFactory{

	public static final SerializerFactory INSTANCE = new JacksonSerializerFactory();
	
	@Override
	public Serializer create() {
		return JacksonSerializer.createParametricType(Message.class);
	}

}
