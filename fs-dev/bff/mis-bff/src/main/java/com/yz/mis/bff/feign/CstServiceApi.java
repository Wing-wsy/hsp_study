package com.yz.mis.bff.feign;

import com.yz.common.result.GraceResult;
import com.yz.model.bo.cst.SearchUserBriefInfoBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "cst-service", path = "/cst/user")
public interface CstServiceApi {

    @PostMapping("searchUserBriefInfo")
    public GraceResult searchUserBriefInfo(SearchUserBriefInfoBO bo);
}

