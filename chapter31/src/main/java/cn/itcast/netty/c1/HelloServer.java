package cn.itcast.netty.c1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author wing
 * @create 2024/10/14
 */
public class HelloServer {
    public static void main(String[] args) {
        // 1. ServerBootstrap 启动器，负责组装 netty 组件，启动服务器
        new ServerBootstrap()
                // 2. BossEventLoop(负责处理可连接事件), WorkerEventLoop(selector, thread)（负责处理可读事件）
                .group(new NioEventLoopGroup())
                // 3. 选择服务器的 ServerSocketChannel 实现
                .channel(NioServerSocketChannel.class)
                // 4. 告诉 WorkerEventLoop 有可读事件时执行哪些逻辑和操作（handler）
                .childHandler(
                    // 5. channel 代表和客户端进行数据读写的通道 Initializer 初始化 负责添加别的 handler
                    new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        // 启动服务端后，initChannel 不会立即执行， 与客户端连接建立后才执行
                        protected void initChannel(NioSocketChannel ch) throws Exception{
                            // 6. 添加具体的 handler
                            ch.pipeline().addLast(new StringDecoder()); // 将 ByteBuf 转换成字符串
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){ // 自定义 handler
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
                                    // 打印上一步转换好的字符串
                                    System.out.println(msg);
                                }
                            });
//                            ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() { // 自定义 handler
//                                @Override // 读事件
//                                protected void channelRead0(ChannelHandlerContext ctx, String msg) {
//                                    // 打印上一步转换好的字符串
//                                    System.out.println(msg);
//                                }
//                            });
                        }
                })
                // 7. 绑定监听端口
                .bind(8088);
    }
}
