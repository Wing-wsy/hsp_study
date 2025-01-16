package com.yz.cst.feign;

import com.yz.common.result.GraceResult;
import com.yz.model.bo.odr.SearchOrderByOrderIdBO;
import com.yz.model.bo.odr.SearchOrderByUserBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "odr-service", path = "odr")
public interface OdrServiceApi {

    @PostMapping("/order/searchOrder")
    public GraceResult searchOrder(SearchOrderByOrderIdBO bo);

    @PostMapping("/order/searchOrderByUser")
    public GraceResult searchOrderByUser(SearchOrderByUserBO bo);
}

