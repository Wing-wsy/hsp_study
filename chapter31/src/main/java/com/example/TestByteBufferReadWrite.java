package com.example;

import java.nio.ByteBuffer;

import static com.example.ByteBufferUtil.debugAll;

/**
 * @author wing
 * @create 2024/10/22
 */
public class TestByteBufferReadWrite {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte)0x61);
        debugAll(buffer);
        buffer.put(new byte[]{0x62, 0x63, 0x64});
        debugAll(buffer);
        buffer.flip();// 切换至读模式
        debugAll(buffer);
        System.out.println("================= get =================");
        System.out.println(buffer.get());
        debugAll(buffer);
        System.out.println("================= compact =================");
        buffer.compact();
        debugAll(buffer);
        buffer.flip();// 切换至读模式
        System.out.println("================= flip =================");
//        System.out.println(buffer.get());
        System.out.println(buffer.get(0));
        System.out.println(buffer.get(1));
        System.out.println(buffer.get(2));
        debugAll(buffer);
        System.out.println(buffer.get());
        debugAll(buffer);
        buffer.mark();
//        buffer.put((byte)0x65);
//        debugAll(buffer);

    }
}
