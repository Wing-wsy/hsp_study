package org.itzixi.netty.websocket;

import com.a3test.component.idworker.IdWorkerConfigBean;
import com.a3test.component.idworker.Snowflake;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.itzixi.constant.basic.Strings;
import org.itzixi.enums.MsgTypeEnum;
import org.itzixi.netty.mq.MessagePublisher;
import org.itzixi.netty.utils.JedisPoolUtils;
import org.itzixi.netty.utils.ZookeeperRegister;
import org.itzixi.pojo.netty.ChatMsg;
import org.itzixi.pojo.netty.DataContent;
import org.itzixi.pojo.netty.NettyServerNode;
import org.itzixi.utils.JsonUtils;
import org.itzixi.utils.LocalDateUtils;
import redis.clients.jedis.Jedis;
import java.time.LocalDateTime;

/**
 * 创建自定义助手类
 * @Auther
 */
// SimpleChannelInboundHandler: 对于请求来说，相当于入站(入境)
// TextWebSocketFrame: 用于为websocket专门处理的文本数据对象，Frame是数据(消息)的载体
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 用于记录和管理所有客户端的channel组
    public static ChannelGroup clients =
            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                TextWebSocketFrame msg) throws Exception {
        // 获得客户端传输过来的消息
        String content = msg.text();

        LocalDateUtils.printByDatetimePattern("接受到的数据：" + content);
        // 1. 获取客户端发来的消息并且解析
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        ChatMsg chatMsg = dataContent.getChatMsg();

        String msgText = chatMsg.getMsg();
        String receiverId = chatMsg.getReceiverId();
        String senderId = chatMsg.getSenderId();

        // TODO 这个域名需要修改
        // 判断是否黑名单 start
        // 如果双方只要有一方是黑名单，则终止发送
//        GraceJSONResult result = OkHttpUtil.get("http://127.0.0.1:1000/friendship/isBlack?friendId1st=" + receiverId
//                                                                + "&friendId2nd=" + senderId);
//        boolean isBlack = (Boolean)result.getData();
        boolean isBlack = false;  // 测试写死非黑名单
        LocalDateUtils.printByDatetimePattern("当前的黑名单关系为: " + isBlack);
        if (isBlack) {
            return;
        }
        // 判断是否黑名单 end

        // 时间校准，以服务器的时间为准
        chatMsg.setChatTime(LocalDateTime.now());
        Integer msgType = chatMsg.getMsgType();

        // 获取channel
        Channel currentChannel = ctx.channel();
        String currentChannelId = currentChannel.id().asLongText();
        String currentChannelIdShort = currentChannel.id().asShortText();

        LocalDateUtils.printByDatetimePattern("客户端currentChannelId：" + currentChannelId);
        LocalDateUtils.printByDatetimePattern("客户端currentChannelIdShort：" + currentChannelIdShort);

        // 2. 判断消息类型，根据不同的类型来处理不同的业务
        if (msgType == MsgTypeEnum.CONNECT_INIT.type) {
            LocalDateUtils.printByDatetimePattern("消息类型【第一次(或重连)初始化连接】：" + msgType);
            // 当websocket初次open的时候，初始化channel，把channel和用户userid管理起来
            UserChannelSession.putMultiChannels(senderId, currentChannel);
            UserChannelSession.putUserChannelIdRelation(currentChannelId, senderId);

            NettyServerNode minNode = dataContent.getServerNode();
            LocalDateUtils.printByDatetimePattern("minNode=" + minNode);
            // 初次连接后，该节点下的在线人数累加
            ZookeeperRegister.incrementOnlineCounts(minNode);

            // 获得ip+端口，在redis中设置关系，以便在前端设备断线后减少在线人数
            Jedis jedis = JedisPoolUtils.getJedis();
            jedis.set(senderId, JsonUtils.objectToJson(minNode));
        }
        else if (msgType == MsgTypeEnum.WORDS.type
                || msgType == MsgTypeEnum.IMAGE.type
                || msgType == MsgTypeEnum.VIDEO.type
                || msgType == MsgTypeEnum.VOICE.type
        ) {
            LocalDateUtils.printByDatetimePattern("消息类型【发送类型】：" + msgType);

            // 方式一：
            // 此处为mq异步解耦，保存信息到数据库，数据库无法获得信息的主键id，
            // 所以此处可以用snowflake直接生成唯一的主键id
            Snowflake snowflake = new Snowflake(new IdWorkerConfigBean());
            String sid = snowflake.nextId();
            LocalDateUtils.printByDatetimePattern("sid = " + sid);

            chatMsg.setMsgId(sid);

            if (msgType == MsgTypeEnum.VOICE.type) {
                chatMsg.setIsRead(false);
            }

            dataContent.setChatMsg(chatMsg);
            String chatTimeFormat = LocalDateUtils
                    .format(chatMsg.getChatTime(),
                            LocalDateUtils.DATETIME_PATTERN_2);
            dataContent.setChatTime(chatTimeFormat);
            dataContent.setExtend(currentChannelId);
            // 将消息广播到队列
            MessagePublisher.sendMsgToOtherNettyServer(JsonUtils.objectToJson(dataContent));

            // 把聊天信息作为mq的消息发送给消费者进行消费处理(最终保存到数据库)
            MessagePublisher.sendMsgToSave(chatMsg);
        } else {
            LocalDateUtils.printByDatetimePattern("特殊类型...");
        }

        // 打印
        UserChannelSession.outputMulti();
    }

    /**
     * 客户端连接到服务端之后(打开链接)
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel currentChannel = ctx.channel();
        String currentChannelId = currentChannel.id().asLongText();
        LocalDateUtils.printByDatetimePattern("客户端建立连接，channel对应的长id为：" + currentChannelId);

        // 获得客户端的channel，并且存入到ChannelGroup中进行管理(作为一个客户端群组)
        clients.add(currentChannel);
    }

    /**
     * 关闭连接，移除channel
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel currentChannel = ctx.channel();
        String currentChannelId = currentChannel.id().asLongText();
        LocalDateUtils.printByDatetimePattern("客户端关闭连接，channel对应的长id为：" + currentChannelId);

        // 移除多余的会话
        String userId = UserChannelSession.getUserIdByChannelId(currentChannelId);
        UserChannelSession.removeUselessChannels(userId, currentChannelId);

        clients.remove(currentChannel);

        LocalDateUtils.printByDatetimePattern("【handlerRemoved】客户端关闭连接，在线人数累减");
        NettyServerNode minNode = null;
        try {
            // zk中在线人数累减
            Jedis jedis = JedisPoolUtils.getJedis();
            minNode = JsonUtils.jsonToPojo(jedis.get(userId),
                    NettyServerNode.class);
        } catch (Exception e) {
            e.printStackTrace();
            LocalDateUtils.printByDatetimePattern("在线人数累减前出错了...");
        }
        if (minNode != null) {
            ZookeeperRegister.decrementOnlineCounts(minNode);
        } else {
            LocalDateUtils.printByDatetimePattern("minNode为null...");

        }
    }

    /**
     * 发生异常并且捕获，移除channel
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 打印异常堆栈
        cause.printStackTrace();

        Channel currentChannel = ctx.channel();
        String currentChannelId = currentChannel.id().asLongText();
        LocalDateUtils.printByDatetimePattern("发生异常捕获，channel对应的长id为：" + currentChannelId);

        // 发生异常之后关闭连接(关闭channel)
        ctx.channel().close();
        // 随后从ChannelGroup中移除对应的channel
        clients.remove(currentChannel);

        // 移除多余的会话
        String userId = UserChannelSession.getUserIdByChannelId(currentChannelId);
        UserChannelSession.removeUselessChannels(userId, currentChannelId);

        LocalDateUtils.printByDatetimePattern("【exceptionCaught】客户端关闭连接，在线人数累减");
        // zk中在线人数累减
        Jedis jedis = JedisPoolUtils.getJedis();
        NettyServerNode minNode = JsonUtils.jsonToPojo(jedis.get(userId),
                NettyServerNode.class);
        ZookeeperRegister.decrementOnlineCounts(minNode);
    }
}
