package com.bfxy.rabbit.common.serializer;

/**
 * 	序列化和反序列化的接口
 */
public interface Serializer {

	/** 传一个对象，序列化成字节数组 */
	byte[] serializeRaw(Object data);

	/** 传一个对象，序列化成字符串 */
	String serialize(Object data);

	/** 传字符串，反序列化成指定泛型的对象 */
	<T> T deserialize(String content);

	/** 传字节数组，反序列化成指定泛型的对象 */
	<T> T deserialize(byte[] content);
	
}
