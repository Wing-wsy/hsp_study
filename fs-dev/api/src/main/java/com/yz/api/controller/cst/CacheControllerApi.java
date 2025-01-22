package com.yz.api.controller.cst;

import com.yz.common.result.GraceResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 缓存控制器
 */
@Tag(name = "CacheController", description = "缓存控制器")
@RequestMapping("/cache")
public interface CacheControllerApi {

    @PostMapping("/refreshResponseResult")
    @Operation(summary = "刷新系统响应结果类型")
    public GraceResult refreshResponseResult();

}
