package com.yz.cst.controller;

import com.yz.common.exception.MyCustomException;
import com.yz.common.result.GraceResult;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.model.bo.cst.TestBo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 */
@Slf4j
@RestController
@RequestMapping("h")
@Tag(name = "HelloController", description = "测试控制器")
public class HelloController {

    @GetMapping("hello")
    public GraceResult hello() {
        System.out.println("hello");
        return GraceResult.ok();
    }

    @GetMapping("test-get")
    @Operation(summary = "测试接口get")
    public GraceResult testGet(TestBo testBo) {
        System.out.println(testBo);
        return GraceResult.ok();
    }

    @PostMapping("test-post")
    @Operation(summary = "测试接口post")
    public GraceResult testPost(@RequestBody TestBo testBo) {
        System.out.println(testBo);
        return GraceResult.ok();
    }
}
