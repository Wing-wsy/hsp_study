package com.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author wing
 * @create 2024/10/22
 */
public class TestGatheringWrites {
    public static void main(String[] args) throws FileNotFoundException {
        ByteBuffer b1 = StandardCharsets.UTF_8.encode( "hello");
        ByteBuffer b2 = StandardCharsets.UTF_8.encode("world");
        ByteBuffer b3 = StandardCharsets.UTF_8.encode("你好");
        try (FileChannel channel = new RandomAccessFile("/Users/wing/architect/github/project/basics/chapter31/src/main/java/com/example/netty/word.txt", "rw").getChannel()) {
            channel.write(new ByteBuffer[]{b1,b2,b3});
        } catch (Exception e) {
            e.printStackTrace();
        }

        }
}
