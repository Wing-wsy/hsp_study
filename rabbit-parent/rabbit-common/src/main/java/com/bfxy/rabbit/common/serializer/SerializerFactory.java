package com.bfxy.rabbit.common.serializer;


/**
 * 	序列化和反序列化工厂接口
 */
public interface SerializerFactory {
	
	Serializer create();
}
