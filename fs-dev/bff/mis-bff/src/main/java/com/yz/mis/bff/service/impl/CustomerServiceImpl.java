package com.yz.mis.bff.service.impl;

import com.yz.common.exception.GraceException;
import com.yz.common.result.GraceResult;
import com.yz.common.util.JSONUtils;
import com.yz.common.util.ListUtils;
import com.yz.cst.mapper.TUserMapper;
import com.yz.mis.bff.feign.CstServiceApi;
import com.yz.mis.bff.feign.OdrServiceApi;
import com.yz.mis.bff.service.CustomerService;
import com.yz.model.bo.cst.SearchUserBriefInfoBO;
import com.yz.model.bo.odr.SearchOrderByUserBO;
import com.yz.model.from.mis_bff.SearchOrderAndUserBriefInfoFrom;
import com.yz.model.from.mis_bff.SearchUserBriefInfoFrom;
import com.yz.model.res.mis_bff.SearchOrderAndUserBriefInfoRes;
import com.yz.model.res.mis_bff.SearchUserBriefInfoRes;
import com.yz.model.vo.cst.UserBriefInfoVO;
import com.yz.model.vo.odr.OrderVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

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
        /*
            允许bff层使用mapper进行简单查询（比如：tUserMapper.selectById(userId)），但不建议使用自定义SQL复杂查询，
            因为bff职责只是对子服务的返回的数据进行整合
         */
//        TUser tUser = tUserMapper.selectById(userId);

        // 1.需要用户数据，请求用户子服务
        SearchUserBriefInfoBO bo_1 = new SearchUserBriefInfoBO();
        bo_1.setUserId(userId);
        GraceResult result_1 = cstServiceApi.searchUserBriefInfo(bo_1);
        if (!result_1.getSuccess()) {
            GraceException.displayCustom(result_1.getCode());
        }
        UserBriefInfoVO userBriefInfoVO = JSONUtils.mapToBean(result_1.getData(), UserBriefInfoVO.class);

        // 2.组装数据
        SearchUserBriefInfoRes res = new SearchUserBriefInfoRes();
        res.setUserBriefInfoVO(userBriefInfoVO);
        return res;
    }

    @Override
    public SearchOrderAndUserBriefInfoRes searchOrderAndUserBriefInfo(SearchOrderAndUserBriefInfoFrom from) {
        Long userId = from.getUserId();

        // 1.需要用户数据，请求用户子服务
        SearchUserBriefInfoBO bo_1 = new SearchUserBriefInfoBO();
        bo_1.setUserId(userId);
        GraceResult result_1 = cstServiceApi.searchUserBriefInfo(bo_1);
        if (!result_1.getSuccess()) {
            GraceException.displayCustom(result_1.getCode());
        }
        UserBriefInfoVO userBriefInfoVO = JSONUtils.mapToBean(result_1.getData(), UserBriefInfoVO.class);

        // 2.需要订单数据，请求订单子服务
        SearchOrderByUserBO bo_2 = new SearchOrderByUserBO();
        bo_2.setUserId(userId);
        GraceResult result_2 = odrServiceApi.searchOrderByUser(bo_2);
        if (!result_2.getSuccess()) {
            GraceException.displayCustom(result_2.getCode());
        }
        List<OrderVO> orderVOList = ListUtils.mapToList(result_2.getData(),OrderVO.class);

        // 3.组装数据
        SearchOrderAndUserBriefInfoRes res = new SearchOrderAndUserBriefInfoRes();
        res.setUserBriefInfoVO(userBriefInfoVO);
        res.setOrderVOList(orderVOList);
        return res;
    }
}
