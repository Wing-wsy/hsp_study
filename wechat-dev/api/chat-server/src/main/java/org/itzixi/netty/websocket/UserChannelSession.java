package org.itzixi.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.itzixi.pojo.netty.DataContent;
import org.itzixi.utils.JsonUtils;
import org.itzixi.utils.LocalDateUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会话管理
 * 用户id和channel的关联处理
 * @Auther
 */
public class UserChannelSession {

    // 初版目前只允许用户单设备登录
    // 用于多端同时接受消息，允许同一个账号在多个设备同时在线，比如iPad、iPhone、Mac等设备同时收到消息【暂不实现】
    // 单聊：key: userId, value: 用户单个channel
    // 群聊：key: 群ID, value: 当前群所有用户channel
    private static Map<String, List<Channel>> multiSession = new HashMap<>();

    // 用于记录用户id和客户端channel id的关联关系
    // key: channelId , value: userId
    private static Map<String, String> userChannelIdRelation = new HashMap<>();

    public static void putUserChannelIdRelation(String channelId, String senderId) {
        userChannelIdRelation.put(channelId, senderId);
    }
    public static void removeUserChannelIdRelation(String channelId) {
        userChannelIdRelation.remove(channelId);
    }
    public static String getUserIdByChannelId(String channelId) {
        return userChannelIdRelation.get(channelId);
    }

    public static void putMultiChannels(String userId, Channel channel) {
        List<Channel> channels = new ArrayList<>();
        channels.add(channel);
        multiSession.put(userId, channels);
    }
    public static List<Channel> getMultiChannels(String receiverId) {
        return multiSession.get(receiverId);
    }

    public static void removeUselessChannels(String senderId, String channelId) {
        List<Channel> channels = getMultiChannels(senderId);
        if (channels == null || channels.size() == 0)
            return;

        if (channels.size() == 1) {
            multiSession.remove(senderId);
            return;
        }

        for (int i = 0 ; i < channels.size() ; i ++) {
            Channel tempChannel = channels.get(i);
            if (tempChannel.id().asLongText().equals(channelId)) {
                channels.remove(i);
            }
        }

        multiSession.put(senderId, channels);
    }

    public static void outputMulti() {
        LocalDateUtils.print("++++++++++++++++++");

        int i = 1;
        for (Map.Entry<String, List<Channel>> entry : multiSession.entrySet()) {
            LocalDateUtils.print("----- 用户 {} -----", i);
            LocalDateUtils.print("UserId: " + entry.getKey());
            List<Channel> temp = entry.getValue();
            for (Channel c : temp) {
                LocalDateUtils.print("\t ChannelId: " + c.id().asLongText());
            }
            i++;
        }

        LocalDateUtils.print("++++++++++++++++++");
    }

    public static void sendToTarget(List<Channel> receiverChannels, DataContent dataContent) {
        ChannelGroup clients = ChatHandler.clients;
        if (receiverChannels == null)
            return;

        for (Channel c : receiverChannels) {
            Channel findChannel = clients.find(c.id());
            if (findChannel != null) {
                findChannel.writeAndFlush(
                        new TextWebSocketFrame(
                                JsonUtils.objectToJson(dataContent)));
            }
        }
    }

    //    public static List<Channel> getMyOtherChannels(String userId, String channelId) {
//        List<Channel> channels = getMultiChannels(userId);
//        if (channels == null || channels.size() == 0) {
//            return null;
//        }
//
//        List<Channel> myOtherChannels = new ArrayList<>();
//        for (int i = 0 ; i < channels.size() ; i ++) {
//            Channel tempChannel = channels.get(i);
//            // 排除当前自己的手机
//            if (!tempChannel.id().asLongText().equals(channelId)) {
//                myOtherChannels.add(tempChannel);
//            }
//        }
//        return myOtherChannels;
//    }

}
