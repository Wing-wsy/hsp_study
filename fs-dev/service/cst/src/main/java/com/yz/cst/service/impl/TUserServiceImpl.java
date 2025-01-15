package com.yz.cst.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.yz.common.util.snow.SnowUtil;
import com.yz.cst.mapper.TUserMapper;
import com.yz.cst.service.TUserService;
import com.yz.model.dto.cst.SearchUserBriefInfoDTO;
import com.yz.model.entity.TUser;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 测试
 */
@Service
public class TUserServiceImpl implements TUserService {
    @Resource
    private TUserMapper tUserMapper;

    @Override
    public SearchUserBriefInfoDTO searchCustomerBriefInfo(Long userId) {
        // mybasits plus 内置方法
        TUser tUser = tUserMapper.selectById(userId);
//        int i = 1/0;

//        tUser.setUserId(SnowUtil.nextId());
//        tUser.setName("createName222");
//        tUserMapper.insert(tUser);

//        tUser.setName("createNameupdate");
//        tUserMapper.updateById(tUser);

        // 自定义方法
//        TUser tUser1 = tUserMapper.getById(userId);
        SearchUserBriefInfoDTO dto = BeanUtil.toBean(tUser,SearchUserBriefInfoDTO.class);
        return dto;
    }
}
