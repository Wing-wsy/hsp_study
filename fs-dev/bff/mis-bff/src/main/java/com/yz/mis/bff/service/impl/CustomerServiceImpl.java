package com.yz.mis.bff.service.impl;

import com.yz.common.result.GraceResult;
import com.yz.cst.mapper.TUserMapper;
import com.yz.mis.bff.controller.bo.SearchUserByIdBO;
import com.yz.mis.bff.service.CustomerService;
import com.yz.model.entity.TUser;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private TUserMapper tUserMapper;

    @Override
    public GraceResult searchById(SearchUserByIdBO bo) {

        // 允许bff层使用mapper进行简单查询，但不建议使用自定义SQL复杂查询
        // bff职责只是对微服务的返回的数据进行整合
        TUser tUser = tUserMapper.selectById(bo.getUserId());

        // 组装微服务返回的数据
        // 组装好前端所需要的数据并返回

        System.out.println("CustomerServiceImpl.....");
        return GraceResult.ok();
    }
}
