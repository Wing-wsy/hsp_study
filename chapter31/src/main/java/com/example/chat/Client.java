package com.example.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author wing
 * @create 2024/10/9
 */
public class Client {

    // 1.定义客户端相关属性
    private Selector selector;
    private static int PORT = 9999;
    private SocketChannel socketChannel;

    // 2.初始化客户端信息
    public Client() {
        try {
            // a.创建选择器
            selector = Selector.open();
            // b.连接服务器
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", PORT));
            // c.设置非阻塞模式
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            System.out.println("当前客户端准备完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        // 定义一个线程专门负责监听服务端发送过来的读消息事件
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.readInfo();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String msg = sc.nextLine();
            client.sendToServer(msg);
        }
    }

    private void sendToServer(String s) {
        try {
            socketChannel.write(ByteBuffer.wrap(("波仔说：" + s).getBytes()));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readInfo() throws IOException {
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel)key.channel();
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    sc.read(buf);
                    System.out.println(new String(buf.array()).trim());
                }
                iterator.remove();
            }

        }
    }
}
