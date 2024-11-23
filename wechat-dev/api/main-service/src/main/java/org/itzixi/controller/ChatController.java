package org.itzixi.controller;

import jakarta.annotation.Resource;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.service.ChatMessageService;
import org.itzixi.utils.PagedGridResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Auther
 */
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

}
