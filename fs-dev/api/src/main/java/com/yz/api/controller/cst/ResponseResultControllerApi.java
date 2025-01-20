package com.yz.api.controller.cst;

import com.yz.common.result.GraceResult;
import com.yz.model.bo.common.CommonLanguageBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试控制层2
 */
@Tag(name = "系统响应结果类型控制层", description = "系统返回的错误编码")
@RequestMapping("/result")
public interface ResponseResultControllerApi {

    @PostMapping("/searchResponseResult")
    @Operation(summary = "查询系统响应结果类型")
    public GraceResult searchResponseResult(CommonLanguageBO bo);

}
