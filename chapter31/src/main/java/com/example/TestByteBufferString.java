package com.example;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import static com.example.ByteBufferUtil.debugAll;

/**
 * @author wing
 * @create 2024/10/22
 */
public class TestByteBufferString {
    public static void main(String[] args) {
        // 1 字符串转 buffer
        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        buffer1.put("hello".getBytes());
        debugAll(buffer1);

        // 2 字符串转 buffer
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello");
        debugAll(buffer2);

        // 3 字符串转 buffer
        ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes());
        debugAll(buffer3);

        // 4 buffer 转字符串
        String str1 = StandardCharsets.UTF_8.decode(buffer2).toString();
        System.out.println(str1);
    }
}
