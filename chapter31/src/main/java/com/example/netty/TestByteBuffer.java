package com.example.netty;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author wing
 * @create 2024/10/10
 */
public class TestByteBuffer {
    public static void main(String[] args) {
        // FileChannel
        // 1. 输入输出流 2.RandomAccessFile
        try (FileChannel channel = new FileInputStream("/Users/wing/architect/github/project/basics/chapter31/src/main/java/com/example/netty/data.txt").getChannel()) {
            // 准备缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true) {
                // 从 channel 读取数据，向 buffer 写入
                int len = channel.read(buffer);
                System.out.println("读取到的字节数：" + len);
                if (len == -1) { // 没有内容了
                    break;
                }
                // 打印buffer的内容
                buffer.flip();// 切换至读模式
                // 是否还有剩余未读数据
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    System.out.println((char) b);
                }
                // 切换为写模式
                buffer.clear();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
