package com.yz.api.controller.cst;

import com.yz.common.result.GraceResult;
import com.yz.model.bo.cst.TestBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试控制层1
 */
@Tag(name = "HelloController", description = "测试控制器")
@RequestMapping("h")
public interface HelloControllerApi {

    @Operation(summary = "hello接口")
    @GetMapping("hello")
    public GraceResult hello();

    @GetMapping("test-get")
    @Operation(summary = "测试接口get")
    public GraceResult testGet(TestBO testBo);

    @PostMapping("test-post")
    @Operation(summary = "测试接口post")
    public GraceResult testPost(TestBO testBo);
}
