package com.yz.mis.bff.controller;

import com.yz.api.controller.mis_bff.CustomerControllerApi;
import com.yz.base.bff.controller.BffBaseController;
import com.yz.common.result.GraceResult;
import com.yz.mis.bff.service.CustomerService;
import com.yz.model.from.mis_bff.SearchUserBriefInfoFrom;
import com.yz.model.res.mis_bff.SearchUserBriefInfoRes;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CustomerController extends BffBaseController implements CustomerControllerApi {
    @Resource
    private CustomerService customerService;

    public GraceResult searchUserBriefInfo(@RequestBody @Valid SearchUserBriefInfoFrom from) {
//        log.debug("debug!~~~~");
//        log.info("info!~~~~");
//        log.warn("warn!~~~~");
//        log.error("error!~~~~");
//        int i = 1/0;
        SearchUserBriefInfoRes searchUserBriefInfoRes = customerService.searchUserBriefInfo(from);
        return GraceResult.ok(searchUserBriefInfoRes);
    }

}

