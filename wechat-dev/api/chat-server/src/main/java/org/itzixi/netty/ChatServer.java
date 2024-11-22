package org.itzixi.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.itzixi.netty.http.HttpServerInitializer;
import org.itzixi.netty.websocket.WSServerInitializer;
//import org.itzixi.netty.websocket.WSServerInitializer;

/**
 * ChatServer: Netty 服务的启动类(服务器)
 * @Auther
 */
public class ChatServer {

    public static void main(String[] args) throws Exception {

        /*
           定义主从线程组
           三种线程模型分别是：
           1单线程（一个线程处理全部的channel，即接收连接也处理消息）
           2多线程（一个线程只负责接收连接后，交给后面的线程组进行消息处理）
           3主从线程（多个线程只负责接收连接后，交给后面的线程组进行消息处理
        */
        // 定义主线程池，用于接受客户端的连接，但是不做任何处理，比如老板会谈业务，拉到业务就会交给下面的员工去做了
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 定义从线程池，处理主线程池交过来的任务，公司业务员开展业务，完成老板交代的任务
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 构建Netty服务器
            ServerBootstrap server = new ServerBootstrap();     // 服务的启动类
            server.group(bossGroup, workerGroup)                // 把主从线程池组放入到启动类中
                    .channel(NioServerSocketChannel.class)      // 设置Nio的双向通道
//                    .childHandler(new HttpServerInitializer());   // 设置处理器，用于处理workerGroup
                    .childHandler(new WSServerInitializer());   // 设置处理器，用于处理workerGroup

            // 启动server，并且绑定端口号875，同时启动方式为"同步"
            ChannelFuture channelFuture = server.bind(875).sync();
            // 请求：http://127.0.0.1:875

            // 监听关闭的channel
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅的关闭线程池组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
