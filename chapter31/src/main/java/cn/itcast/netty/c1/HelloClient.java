package cn.itcast.netty.c1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Date;

/**
 * @author wing
 * @create 2024/10/14
 */
public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        // 1. 启动类
        new Bootstrap()
                // 2. 添加 EventLoop
                .group(new NioEventLoopGroup())
                // 3. 选择客户端 channel 实现
                .channel(NioSocketChannel.class)
                // 4. 添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override // 与服务器端连接建立后才执行 initChannel
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 将字符串编码成 ByteBuf （和服务器端反过来）
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                // 5. 连接到服务器
                .connect("127.0.0.1", 8088)
                .sync() // 阻塞方法，直到连接建立，才继续往下执行
                .channel() // 代表连接对象
                // 6. 向服务器发送数据
                .writeAndFlush(new Date() + ": hello world!"); // 7
    }
}
