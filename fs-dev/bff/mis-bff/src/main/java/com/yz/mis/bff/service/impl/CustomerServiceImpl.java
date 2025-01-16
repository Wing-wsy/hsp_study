package com.yz.mis.bff.service.impl;

import com.yz.common.exception.GraceException;
import com.yz.common.result.GraceResult;
import com.yz.common.util.JSONUtils;
import com.yz.cst.mapper.TUserMapper;
import com.yz.mis.bff.feign.CstServiceApi;
import com.yz.mis.bff.feign.OdrServiceApi;
import com.yz.mis.bff.service.CustomerService;
import com.yz.model.bo.cst.SearchUserBriefInfoBO;
import com.yz.model.bo.odr.SearchOrderByOrderIdBO;
import com.yz.model.from.mis_bff.SearchUserBriefInfoFrom;
import com.yz.model.res.mis_bff.SearchUserBriefInfoRes;
import com.yz.model.vo.cst.UserBriefInfoVO;
import com.yz.model.vo.odr.OrderVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private TUserMapper tUserMapper;

    @Resource
    private CstServiceApi cstServiceApi;

    @Resource
    private OdrServiceApi odrServiceApi;

    @Override
    public SearchUserBriefInfoRes searchUserBriefInfo(SearchUserBriefInfoFrom from) {

        Long userId = from.getUserId();
        Long orderId = from.getOrderId();

        // 允许bff层使用mapper进行简单查询，但不建议使用自定义SQL复杂查询
        // bff职责只是对微服务的返回的数据进行整合
//        TUser tUser = tUserMapper.selectById(userId);

        // 1.需要用户数据，请求用户微服务
        SearchUserBriefInfoBO bo_1 = new SearchUserBriefInfoBO();
        bo_1.setUserId(userId);
        GraceResult result_1 = cstServiceApi.searchUserBriefInfo(bo_1);
        if (!result_1.getSuccess()) {
            GraceException.displayCustom(result_1.getCode());
        }
        UserBriefInfoVO userBriefInfoVO = JSONUtils.mapToBean(result_1.getData(), UserBriefInfoVO.class);

        // 2.需要订单数据，请求订单微服务
        SearchOrderByOrderIdBO bo_2 = new SearchOrderByOrderIdBO();
        bo_2.setOrderId(orderId);
        GraceResult result_2 = odrServiceApi.searchOrder(bo_2);
        if (!result_2.getSuccess()) {
            GraceException.displayCustom(result_2.getCode());
        }
        OrderVO orderVO = JSONUtils.mapToBean(result_2.getData(), OrderVO.class);

        // 3.组装数据
        SearchUserBriefInfoRes res = new SearchUserBriefInfoRes();
        res.setUserBriefInfoVO(userBriefInfoVO);
        res.setOrderVO(orderVO);
        return res;

    }
}
