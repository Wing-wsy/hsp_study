package cn.itcast.netty.c3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Date;

/**
 * @author wing
 * @create 2024/10/14
 */
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        // 1. 启动类
        ChannelFuture channelFuture = new Bootstrap()
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
                .connect("127.0.0.1", 8088);

        // 2.1 使用 sycn 方法同步处理结果

//        channelFuture.sync(); // 阻塞方法，直到连接建立，才继续往下执行
//        Channel channel = channelFuture.channel();
//        System.out.println("channel:" + channel);
//        channel.writeAndFlush("hello");

        // 2.2 使用 addListener 方法异步处理结果
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            // 在 nio 线程连接建立好只会，会调用 operationComplete
            public void operationComplete(ChannelFuture future) throws Exception {
                Channel channel = future.channel();
                System.out.println("channel:" + channel + ",线程："+ Thread.currentThread().getName());
                channel.writeAndFlush("hello,hi");
            }
        });

    }
}
