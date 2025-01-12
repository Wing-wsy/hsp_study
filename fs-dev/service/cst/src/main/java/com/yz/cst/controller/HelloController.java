package com.yz.cst.controller;

import com.yz.api.controller.cst.HelloControllerApi;
import com.yz.common.result.GraceResult;
import com.yz.model.bo.cst.TestBo;
import com.yz.service.base.controller.ServiceBaseController;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
public class HelloController extends ServiceBaseController implements HelloControllerApi {

    public GraceResult hello() {
        System.out.println("hello");
        serviceBaseController();
        return GraceResult.ok();
    }

    public GraceResult testGet(TestBo testBo) {
        System.out.println(testBo);
        return GraceResult.ok();
    }

    public GraceResult testPost(@RequestBody TestBo testBo) {
        System.out.println(testBo);
        return GraceResult.ok();
    }
}
