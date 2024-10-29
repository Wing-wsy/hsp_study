package cn.itcast.netty.chardemo.server.session;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wing
 * @create 2024/10/22
 */
public class SessionMemoryImpl implements Session{
    private final Map<String, Channel> usernameChannelMap = new ConcurrentHashMap<>();
    private final Map<Channel, String> channelUsernameMap = new ConcurrentHashMap<>();
    private final Map<Channel, Map<String, Object>> channelAttributesMap = new ConcurrentHashMap<>();

    @Override
    public void bind(Channel channel, String username) {
        usernameChannelMap.put(username, channel);
        channelUsernameMap.put(channel, username);
        channelAttributesMap.put(channel,new ConcurrentHashMap<>());
    }

    @Override
    public void unbind(Channel channel) {
        String username = channelUsernameMap.remove(channel);
        usernameChannelMap.remove(username);
        channelAttributesMap.remove(channel);

    }

    @Override
    public Object getAttribute(Channel channel, String name) {
        return null;
    }

    @Override
    public void setAttribute(Channel channel, String name, Object value) {

    }

    @Override
    public Channel getChannel(String username) {
        return null;
    }
}
