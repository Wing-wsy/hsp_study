package cn.itcast.project.server;

//import cn.itcast.message.GroupJoinRequestMessage;
import cn.itcast.project.message.LoginRequestMessage;
import cn.itcast.project.message.LoginResponseMessage;
import cn.itcast.project.protocol.MessageCodecSharable;
//import cn.itcast.server.handler.*;
import cn.itcast.project.protocol.ProcotolFrameDecoder;
import cn.itcast.project.server.service.UserServiceFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatServer {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
//        LoginRequestMessageHandler LOGIN_HANDLER = new LoginRequestMessageHandler();
//        ChatRequestMessageHandler CHAT_HANDLER = new ChatRequestMessageHandler();
//        GroupCreateRequestMessageHandler GROUP_CREATE_HANDLER = new GroupCreateRequestMessageHandler();
//        GroupJoinRequestMessageHandler GROUP_JOIN_HANDLER = new GroupJoinRequestMessageHandler();
//        GroupMembersRequestMessageHandler GROUP_MEMBERS_HANDLER = new GroupMembersRequestMessageHandler();
//        GroupQuitRequestMessageHandler GROUP_QUIT_HANDLER = new GroupQuitRequestMessageHandler();
//        GroupChatRequestMessageHandler GROUP_CHAT_HANDLER = new GroupChatRequestMessageHandler();
//        QuitHandler QUIT_HANDLER = new QuitHandler();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss, worker);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ProcotolFrameDecoder());
                    ch.pipeline().addLast(LOGGING_HANDLER);
                    ch.pipeline().addLast(MESSAGE_CODEC);
                    ch.pipeline().addLast(new SimpleChannelInboundHandler<LoginRequestMessage>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
                            String username = msg.getUsername();
                            String password = msg.getPassword();
                            boolean login = UserServiceFactory.getUserService().login(username, password);
                            LoginResponseMessage message;
                            if (login) {
                                message = new LoginResponseMessage(true, "登录成功");
                            } else {
                                message = new LoginResponseMessage(false, "用户名或密码错误");
                            }
                            ctx.writeAndFlush(message);
                        }
                    });


                    // 用来判断是不是 读空闲时间过长，或 写空闲时间过长
                    // 5s 内如果没有收到 channel 的数据，会触发一个 IdleState#READER_IDLE 事件
//                    ch.pipeline().addLast(new IdleStateHandler(5, 0, 0));
//                    // ChannelDuplexHandler 可以同时作为入站和出站处理器
//                    ch.pipeline().addLast(new ChannelDuplexHandler() {
//                        // 用来触发特殊事件
//                        @Override
//                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
//                            IdleStateEvent event = (IdleStateEvent) evt;
//                            // 触发了读空闲事件
//                            if (event.state() == IdleState.READER_IDLE) {
//                                log.debug("已经 5s 没有读到数据了");
//                                ctx.channel().close();
//                            }
//                        }
//                    });
//                    ch.pipeline().addLast(LOGIN_HANDLER);
//                    ch.pipeline().addLast(CHAT_HANDLER);
//                    ch.pipeline().addLast(GROUP_CREATE_HANDLER);
//                    ch.pipeline().addLast(GROUP_JOIN_HANDLER);
//                    ch.pipeline().addLast(GROUP_MEMBERS_HANDLER);
//                    ch.pipeline().addLast(GROUP_QUIT_HANDLER);
//                    ch.pipeline().addLast(GROUP_CHAT_HANDLER);
//                    ch.pipeline().addLast(QUIT_HANDLER);
                }
            });
            Channel channel = serverBootstrap.bind(8088).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            System.out.println("server errorL" + e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}
