package com.yz.mis.bff.service.impl;

import com.yz.common.exception.GraceException;
import com.yz.common.result.GraceResult;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.common.util.JSONUtils;
import com.yz.cst.mapper.TUserMapper;
import com.yz.mis.bff.feign.CstServiceApi;
import com.yz.mis.bff.service.CustomerService;
import com.yz.model.bo.cst.SearchUserBriefInfoBO;
import com.yz.model.entity.TUser;
import com.yz.model.from.mis_bff.SearchUserBriefInfoFrom;
import com.yz.model.res.mis_bff.SearchUserBriefInfoRes;
import com.yz.model.vo.cst.SearchUserBriefInfoVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private TUserMapper tUserMapper;

    @Resource
    private CstServiceApi cstServiceApi;

    @Override
    public SearchUserBriefInfoRes searchUserBriefInfo(SearchUserBriefInfoFrom from) {
        SearchUserBriefInfoRes res = new SearchUserBriefInfoRes();
        Long userId = from.getUserId();

        // 允许bff层使用mapper进行简单查询，但不建议使用自定义SQL复杂查询
        // bff职责只是对微服务的返回的数据进行整合
        TUser tUser = tUserMapper.selectById(userId);

        // 组装微服务返回的数据
        // 组装好前端所需要的数据并返回
        SearchUserBriefInfoBO bo_1 = new SearchUserBriefInfoBO();
        bo_1.setUserId(userId);
        GraceResult result_1 = cstServiceApi.searchUserBriefInfo(bo_1);
        if (!result_1.getSuccess()) {
            GraceException.display(ResponseStatusEnum.USER_NOT_FIND);
        }
        SearchUserBriefInfoVO searchUserBriefInfoVO = JSONUtils.mapToBean(result_1.getData(), SearchUserBriefInfoVO.class);
        res.setSearchUserBriefInfoVO(searchUserBriefInfoVO);
        return res;
    }
}
