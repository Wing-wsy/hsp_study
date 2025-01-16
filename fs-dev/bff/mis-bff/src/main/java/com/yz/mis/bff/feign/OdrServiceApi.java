package com.yz.mis.bff.feign;

import com.yz.common.result.GraceResult;
import com.yz.model.bo.odr.SearchOrderByOrderIdBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "odr-service", path = "odr")
public interface OdrServiceApi {

    @PostMapping("/order/searchOrder")
    public GraceResult searchOrder(SearchOrderByOrderIdBO bo);

}

