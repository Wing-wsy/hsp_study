package com.yz.cst.controller;

import com.yz.api.controller.cst.HelloControllerApi;
import com.yz.common.result.GraceResult;
import com.yz.model.bo.cst.TestBO;
import com.yz.service.base.controller.BaseController;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 测试控制层1
 */
@Slf4j
@RestController
public class HelloController extends BaseController implements HelloControllerApi {

    public GraceResult hello() {
//        System.out.println("hello");
        log.info("info");
        log.warn("info");
        log.error("info");
        int i = 1/0;
        return GraceResult.ok();
    }

    public GraceResult testGet(@Valid TestBO testBo) {
        log.info("testGet");
        GraceResult result = GraceResult.ok();
        return result;
    }

    public GraceResult testPost(@RequestBody TestBO testBo) {
        System.out.println(testBo);
        return GraceResult.ok();
    }
}
