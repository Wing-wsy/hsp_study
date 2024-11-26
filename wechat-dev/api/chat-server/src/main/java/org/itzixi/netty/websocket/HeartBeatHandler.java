package org.itzixi.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 创建心跳助手类
 * @Auther
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 判断evt是否是IdleStateEvent(空闲事件状态，包含 读空闲/写空闲/读写空闲)
        /**
         * 进入下面判断跟这行代码设置的时间有关
         * pipeline.addLast(new IdleStateHandler(
         *                                 8,   // 读 8 秒
         *                                 10,  // 写 10 秒
         *                                 12)); // 所有状态 300 * 60 秒
         */
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state() == IdleState.READER_IDLE) {
//                 System.out.println("进入读空闲...");
            } else if (event.state() == IdleState.WRITER_IDLE) {
//                 System.out.println("进入写空闲...");
            } else if (event.state() == IdleState.ALL_IDLE) {
//                 System.out.println("chennel 关闭前，clients的数量为：" + ChatHandler.clients.size());
                Channel channel = ctx.channel();
                // 关闭无用的channel，以防资源浪费
                channel.close();
//                 System.out.println("chennel 关闭后，clients的数量为：" + ChatHandler.clients.size());
            }
        }
    }
}