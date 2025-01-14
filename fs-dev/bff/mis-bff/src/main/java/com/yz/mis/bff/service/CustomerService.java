package com.yz.mis.bff.service;

import com.yz.common.result.GraceResult;
import com.yz.mis.bff.controller.bo.SearchUserByIdBO;
import org.springframework.stereotype.Service;

public interface CustomerService {

    public GraceResult searchById(SearchUserByIdBO bo);
}
