package com.yz.cst.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.yz.cst.mapper.TUserMapper;
import com.yz.cst.service.TUserService;
import com.yz.model.dto.cst.SearchUserBriefInfoDTO;
import com.yz.model.entity.TUser;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TUserServiceImpl implements TUserService {
    @Resource
    private TUserMapper tUserMapper;

    @Override
    public SearchUserBriefInfoDTO searchCustomerBriefInfo(Long userId) {
        TUser tUser = tUserMapper.selectById(userId);
//        TUser tUser1 = tUserMapper.getById(userId);
        SearchUserBriefInfoDTO dto = BeanUtil.toBean(tUser,SearchUserBriefInfoDTO.class);
        return dto;
    }
}
