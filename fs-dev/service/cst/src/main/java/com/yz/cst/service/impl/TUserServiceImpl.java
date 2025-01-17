package com.yz.cst.service.impl;

import com.yz.common.exception.GraceException;
import com.yz.common.result.GraceResult;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.common.util.BeanUtils;
import com.yz.common.util.snow.SnowUtil;
import com.yz.cst.feign.OdrServiceApi;
import com.yz.cst.mapper.TUserMapper;
import com.yz.cst.service.TUserService;
import com.yz.model.bo.odr.SearchOrderByUserBO;
import com.yz.model.dto.cst.UserBriefInfoDTO;
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

    @Resource
    private OdrServiceApi odrServiceApi;

    @Override
    public UserBriefInfoDTO searchCustomerBriefInfo(Long userId) {
        TUser tUser = tUserMapper.selectById(userId);
        if (tUser == null) {
            GraceException.display(ResponseStatusEnum.USER_NOT_FIND);
        }

        tUser.setUserId(SnowUtil.nextId());
        tUser.setName("createName888");
        tUserMapper.insert(tUser);

//        tUser.setName("createNameupdate999");
//        tUserMapper.updateById(tUser);

        UserBriefInfoDTO dto = BeanUtils.toBean(tUser, UserBriefInfoDTO.class);

        // 演示在cst微服务调用odr微服务
        /*SearchOrderByUserBO bo = new SearchOrderByUserBO();
        bo.setUserId(userId);
        GraceResult result_1 = odrServiceApi.searchOrderByUser(bo);
        if (!result_1.getSuccess()) {
            GraceException.displayCustom(result_1.getCode());
        }*/

        return dto;
    }
}
