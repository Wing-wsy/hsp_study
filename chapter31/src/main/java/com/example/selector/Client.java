package com.example.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * NIO非阻塞通信下的入门案例：客户端开发
 */
public class Client {
    public static void main(String[] args) throws Exception {
        // 1.获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));
        // 2.切换成非阻塞模式
        sChannel.configureBlocking(false);
        // 3.分配指定缓冲区大小
        ByteBuffer buf = ByteBuffer.allocate(1024);
        // 4.发送数据给服务端
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请说：");
            String msg = sc.nextLine();
            buf.put(("波仔：" + msg).getBytes());
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }
    }
}
