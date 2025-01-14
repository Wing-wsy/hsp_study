package com.yz.mis.bff.controller;

import com.yz.common.result.GraceResult;
import com.yz.mis.bff.controller.bo.SearchUserByIdBO;
import com.yz.mis.bff.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@Tag(name = "CustomerController", description = "用户Web接口")
public class CustomerController {
    @Resource
    private CustomerService customerService;

    @PostMapping("/searchById")
    @Operation(summary = "根据ID查找用户")
    public GraceResult searchById(@RequestBody @Valid SearchUserByIdBO bo) {
        System.out.println(111);
        return customerService.searchById(bo);

    }


}

