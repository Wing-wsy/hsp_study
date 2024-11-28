package org.itzixi.controller;

import jakarta.annotation.Resource;
import org.apache.curator.framework.CuratorFramework;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.constant.basic.Strings;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.pojo.netty.NettyServerNode;
import org.itzixi.service.ChatMessageService;
import org.itzixi.utils.JsonUtils;
import org.itzixi.utils.PagedGridResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Auther
 */
//@Slf4j
@RestController
@RequestMapping("chat")
public class ChatController extends BaseInfoProperties {

    @Resource
    private ChatMessageService chatMessageService;

    @PostMapping("getMyUnReadCounts")
    public GraceJSONResult getMyUnReadCounts(String myId) {
        Map map = redis.hgetall(CHAT_MSG_LIST + ":" + myId);
        return GraceJSONResult.ok(map);
    }

    @PostMapping("clearMyUnReadCounts")
    public GraceJSONResult clearMyUnReadCounts(String myId, String oppositeId) {
        redis.setHashValue(CHAT_MSG_LIST + ":" + myId, oppositeId, "0");
        return GraceJSONResult.ok();
    }

    @PostMapping("list/{senderId}/{receiverId}")
    public GraceJSONResult list(@PathVariable("senderId") String senderId,
                                @PathVariable("receiverId") String receiverId,
                                Integer page,
                                Integer pageSize) {

        if (page == null) page = 1;
        if (pageSize == null) page = 20;

        PagedGridResult gridResult = chatMessageService.queryChatMsgList(
                                                                senderId,
                                                                receiverId,
                                                                page,
                                                                pageSize);
        return GraceJSONResult.ok(gridResult);
    }

    @PostMapping("signRead/{msgId}")
    public GraceJSONResult signRead(@PathVariable("msgId") String msgId) {
        chatMessageService.updateMsgSignRead(msgId);
        return GraceJSONResult.ok();
    }


    @Resource(name = "curatorClient")
    private CuratorFramework zkClient;

    /**
     * 获取 netty集群服务器在 zk 中注册的IP地址
     * @return
     * @throws Exception
     */
    @PostMapping("getNettyOnlineInfo")
    public GraceJSONResult getNettyOnlineInfo() throws Exception {

        // 从zookeeper中获得当前已经注册的netty 服务列表
        String path = Strings.SLASH + SERVER_LIST;
        List<String> list = zkClient.getChildren().forPath(path);

        List<NettyServerNode> serverNodeList = new ArrayList<>();
        for (String node:list) {
            String nodeValue = new String(zkClient.getData().forPath(path + "/" + node));
//            System.out.println(nodeValue);
            NettyServerNode serverNode = JsonUtils.jsonToPojo(nodeValue, NettyServerNode.class);
            serverNodeList.add(serverNode);
        }

        // 计算当前哪个zk的node是最少连接，获得[ip:port]并且返回给前端【这里也可以轮训等方式，自己按需实现】
        Optional<NettyServerNode> minNodeOptional = serverNodeList
                .stream()
                .min(Comparator.comparing(nettyServerNode -> nettyServerNode.getOnlineCounts()));
        NettyServerNode minNode = minNodeOptional.get();

//        log.info("getNettyOnlineInfo获取到的数据，{}", minNode);
        return GraceJSONResult.ok(minNode);
    }


}
