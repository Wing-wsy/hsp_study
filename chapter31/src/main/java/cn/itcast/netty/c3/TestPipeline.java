package cn.itcast.netty.c3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.Charset;

/**
 * @author wing
 * @create 2024/10/25
 */
public class TestPipeline {
    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler( new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception{
                                ChannelPipeline pipeline = ch.pipeline();
                                pipeline.addLast("h1",new ChannelInboundHandlerAdapter(){
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
                                        // 打印上一步转换好的字符串
                                        System.out.println("1," + Thread.currentThread().getName());
                                        ByteBuf buf = (ByteBuf) msg;
                                        String name = buf.toString(Charset.defaultCharset());
                                        super.channelRead(ctx,name);
                                    }
                                });
                                pipeline.addLast("h2",new ChannelInboundHandlerAdapter(){
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
                                        // 打印上一步转换好的字符串
                                        System.out.println("2," + Thread.currentThread().getName());
                                        System.out.println("==========" + msg);
                                        super.channelRead(ctx,msg);
                                    }
                                });
                                pipeline.addLast("h3",new ChannelInboundHandlerAdapter(){
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
                                        // 打印上一步转换好的字符串
                                        System.out.println("3," + Thread.currentThread().getName());
                                        super.channelRead(ctx,msg);
                                        // 向客户端写数据
                                        ch.writeAndFlush(ctx.alloc().buffer().writeBytes("server...".getBytes()));
                                    }
                                });
                                pipeline.addLast("h4",new ChannelOutboundHandlerAdapter(){
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        System.out.println("4," + Thread.currentThread().getName());
                                        super.write(ctx, msg, promise);
                                    }
                                });
                                pipeline.addLast("h5",new ChannelOutboundHandlerAdapter(){
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        System.out.println("5," + Thread.currentThread().getName());
                                        super.write(ctx, msg, promise);
                                    }
                                });
                                pipeline.addLast("h6",new ChannelOutboundHandlerAdapter(){
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        System.out.println("6," + Thread.currentThread().getName());
                                        super.write(ctx, msg, promise);
                                    }
                                });
                            }
                        })
                .bind(8088);
    }
}
