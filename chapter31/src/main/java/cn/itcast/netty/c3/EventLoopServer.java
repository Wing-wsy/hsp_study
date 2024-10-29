package cn.itcast.netty.c3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.Charset;

/**
 * @author wing
 * @create 2024/10/24
 */
public class EventLoopServer {
    public static void main(String[] args) {
        // 细分2:创建一个独立的 EventLoopGroup 来执行耗时长的任务
        EventLoopGroup group = new DefaultEventLoopGroup();
        new ServerBootstrap()
                // 细分1:boss 只负责 accept 事件， worker 只负责读写事件（worker 不设置的话，就是CPU数*2，设置成2就是2个worker线程）
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception{
                                ch.pipeline().addLast("handler1",new ChannelInboundHandlerAdapter(){
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
                                        ByteBuf buf = (ByteBuf) msg;
                                        System.out.println(buf.toString(Charset.defaultCharset()) + ", 线程名称：" + Thread.currentThread().getName());
                                        ctx.fireChannelRead(msg); // 让消息传递给下一个 handler
                                    }
                                }).addLast(group, "handler2",new ChannelInboundHandlerAdapter(){
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
                                        ByteBuf buf = (ByteBuf) msg;
                                        System.out.println(buf.toString(Charset.defaultCharset()) + ", 线程名称：" + Thread.currentThread().getName());
                                    }
                                });
                            }
                        })
                .bind(8088);
    }
}
