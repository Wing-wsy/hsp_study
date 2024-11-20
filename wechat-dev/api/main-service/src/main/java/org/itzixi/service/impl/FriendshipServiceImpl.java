package org.itzixi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.enums.FriendRequestVerifyStatus;
import org.itzixi.enums.YesOrNo;
import org.itzixi.mapper.FriendRequestMapper;
import org.itzixi.mapper.FriendRequestMapperCustom;
import org.itzixi.mapper.FriendshipMapper;
import org.itzixi.mapper.FriendshipMapperCustom;
import org.itzixi.pojo.FriendRequest;
import org.itzixi.pojo.Friendship;
import org.itzixi.pojo.bo.NewFriendRequestBO;
import org.itzixi.pojo.vo.ContactsVO;
import org.itzixi.pojo.vo.NewFriendsVO;
import org.itzixi.service.FriendRequestService;
import org.itzixi.service.FriendshipService;
import org.itzixi.utils.PagedGridResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FriendshipServiceImpl extends BaseInfoProperties implements FriendshipService {

    @Resource
    private FriendshipMapper friendshipMapper;

    @Resource
    private FriendshipMapperCustom friendshipMapperCustom;

    @Override
    public Friendship getFriendship(String myId, String friendId) {

        QueryWrapper queryWrapper = new QueryWrapper<FriendRequest>()
                                        .eq("my_id", myId)
                                        .eq("friend_id", friendId);

        return friendshipMapper.selectOne(queryWrapper);
    }

    @Override
    public List<ContactsVO> queryMyFriends(String myId, boolean needBlack) {

        Map<String, Object> map = new HashMap<>();
        map.put("myId", myId);
        map.put("needBlack", needBlack);

        return friendshipMapperCustom.queryMyFriends(map);
    }

    @Transactional
    @Override
    public void updateFriendRemark(String myId,
                                   String friendId,
                                   String friendRemark) {

        QueryWrapper<Friendship> updateWrapper = new QueryWrapper<>();
        updateWrapper.eq("my_id", myId);
        updateWrapper.eq("friend_id", friendId);

        Friendship friendship = new Friendship();
        friendship.setFriendRemark(friendRemark);
        friendship.setUpdatedTime(LocalDateTime.now());

        friendshipMapper.update(friendship, updateWrapper);
    }

    @Transactional
    @Override
    public void updateBlackList(String myId,
                                String friendId,
                                YesOrNo yesOrNo) {

        QueryWrapper<Friendship> updateWrapper = new QueryWrapper<>();
        updateWrapper.eq("my_id", myId);
        updateWrapper.eq("friend_id", friendId);

        Friendship friendship = new Friendship();
        friendship.setIsBlack(yesOrNo.type);
        friendship.setUpdatedTime(LocalDateTime.now());

        friendshipMapper.update(friendship, updateWrapper);
    }

    @Transactional
    @Override
    public void delete(String myId, String friendId) {

        QueryWrapper<Friendship> deleteWrapper1 = new QueryWrapper<>();
        deleteWrapper1.eq("my_id", myId);
        deleteWrapper1.eq("friend_id", friendId);

        friendshipMapper.delete(deleteWrapper1);

        QueryWrapper<Friendship> deleteWrapper2 = new QueryWrapper<>();
        deleteWrapper2.eq("my_id", friendId);
        deleteWrapper2.eq("friend_id", myId);

        friendshipMapper.delete(deleteWrapper2);
    }

    @Override
    public boolean isBlackEachOther(String friendId1st, String friendId2nd) {

        QueryWrapper<Friendship> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("my_id", friendId1st);
        queryWrapper1.eq("friend_id", friendId2nd);
        queryWrapper1.eq("is_black", YesOrNo.YES.type);

        Friendship friendship1st = friendshipMapper.selectOne(queryWrapper1);

        QueryWrapper<Friendship> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("my_id", friendId2nd);
        queryWrapper2.eq("friend_id", friendId1st);
        queryWrapper2.eq("is_black", YesOrNo.YES.type);

        Friendship friendship2nd = friendshipMapper.selectOne(queryWrapper2);

        return friendship1st != null || friendship2nd != null;
    }
}
