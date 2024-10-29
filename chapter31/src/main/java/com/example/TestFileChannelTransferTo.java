package com.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author wing
 * @create 2024/10/23
 */
public class TestFileChannelTransferTo {
    public static void main(String[] args) {
        try (
                FileChannel from = new FileInputStream("/Users/wing/architect/github/project/basics/chapter31/src/main/java/com/example/netty/data.txt").getChannel();
                FileChannel to = new FileOutputStream("/Users/wing/architect/github/project/basics/chapter31/src/main/java/com/example/netty/to.txt").getChannel();
        ) {
            long size = from.size();
            // left 变量代表还剩余多少字节
            for (long left = size; left > 0; ) {
                System.out.println("position:" + (size - left) + " left:" + left);
                // 效率高，底层会利用操作系统的零拷贝进行优化
                left -= from.transferTo((size - left), left, to);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
