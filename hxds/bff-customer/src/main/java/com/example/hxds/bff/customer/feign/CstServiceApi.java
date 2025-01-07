package com.example.hxds.bff.customer.feign;

import com.example.hxds.bff.customer.controller.form.*;
import com.example.hxds.bff.customer.feign.fallback.CstServiceApiFallback;
import com.example.hxds.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

//@FeignClient(value = "hxds-cst")

// 因为都是统一前缀/customer，所以这里配置path = "customer"，下面都可以省略/customer
// fallback = CstServiceApiFallback.class 客户端服务器降级
@FeignClient(value = "hxds-cst",
            path = "customer",
            fallback = CstServiceApiFallback.class)

public interface CstServiceApi {
    @PostMapping("/customer/registerNewCustomer")
    public R registerNewCustomer(RegisterNewCustomerForm form);

    @PostMapping("/customer/login")
    public R login(LoginForm form);

    @PostMapping("/customer/car/insertCustomerCar")
    public R insertCustomerCar(InsertCustomerCarForm form);

    @PostMapping("/customer/car/searchCustomerCarList")
    public R searchCustomerCarList(SearchCustomerCarListForm form);

    @PostMapping("/customer/car/deleteCustomerCarById")
    public R deleteCustomerCarById(DeleteCustomerCarByIdForm form);
}
