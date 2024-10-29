package com.example.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author wing
 * @create 2024/9/21
 */
public class Server {
    // 1.定义一些成员属性：选择器、服务端通道、端口
    private Selector selector;
    private ServerSocketChannel ssChannel;
    private static final int PORT = 9999;

    // 2.定义初始化代码逻辑
    public Server() {
        try {
            // a.创建选择器对象
            selector = Selector.open();
            // b.获取通道
            ssChannel = ServerSocketChannel.open();
            // c.绑定客户端链接的端口
            ssChannel.bind(new InetSocketAddress(PORT));
            // d.设置非阻塞通信模式
            ssChannel.configureBlocking(false);
            // e.把通道注册到选择器上去，并且开始指定接收连接事件
            ssChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 创建服务端对象
        Server server = new Server();
        // 开始监听客户端的各种消息事件：连接、群聊消息、离线消息
        server.listen();

    }

    private void listen() {
        try {
            while (selector.select() > 0) {
                // a. 获取选择器中所有注册通道的就绪事件
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                // b. 开始遍历这些事件
                while (it.hasNext()) {
                    // 提取当前这个事件
                    SelectionKey sk = it.next();
                    // c. 判断这个事件的类型
                    if (sk.isAcceptable()) {
                        // 直接获取当前接入的客户端通道
                        SocketChannel schannel = ssChannel.accept();
                        // 切换成非阻塞模式
                        schannel.configureBlocking(false);
                        // 将本客户端通道注册到选择器
                        schannel.register(selector, SelectionKey.OP_READ);
                    } else if (sk.isReadable()) {
                        // 处理这个客户端的消息
                        readClientData(sk);
                    }
                    // 处理完毕后移除当前事件
                    it.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 接收当前客户端通道的消息，转发给其他全部客户端通道
    private void readClientData(SelectionKey sk) {
        SocketChannel sChannel = null;
        try {
            // 直接得到当前客户端通道
            sChannel = (SocketChannel)sk.channel();
            // 创建缓冲区对象开始接收客户端通道的数据
            ByteBuffer buf = ByteBuffer.allocate(1024);
            int count = sChannel.read(buf);
            if (count > 0) {
                buf.flip();
                // 提取读到的信息
                String msg = new String(buf.array(), 0, buf.remaining());
                System.out.println("接收到了客户端消息：" + msg);
                // 把这个消息推送给全部客户端接收
                sendMsgToAllClient(msg, sChannel);
            }
        } catch (Exception e) {
            try {
                System.out.println("有人离线了：" + sChannel.getRemoteAddress());
                // 当前客户端离线
                sk.cancel(); // 取消注册
                sChannel.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }

    // 把当前客户端的消息数据推送给当前全部在线注册的channel
    private void sendMsgToAllClient(String msg, SocketChannel sChannel) throws IOException {
        System.out.println("服务端开始转发这个消息：当前处理的线程：" + Thread.currentThread().getName());
        for (SelectionKey key : selector.keys()) {
            Channel channel = key.channel();
            // 不要把数据发给自己
            if (channel instanceof SocketChannel && sChannel != channel) {
                ByteBuffer buf = ByteBuffer.wrap(msg.getBytes());
                ((SocketChannel)channel).write(buf);
            }
        }
    }

}
