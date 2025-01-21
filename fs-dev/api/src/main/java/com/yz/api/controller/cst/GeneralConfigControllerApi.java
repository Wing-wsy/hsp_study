package com.yz.api.controller.cst;

import com.yz.common.result.GraceResult;
import com.yz.model.bo.common.CommonLanguageBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 通用配置控制器
 */
@Tag(name = "GeneralConfigController", description = "通用配置控制器")
@RequestMapping("/general-config")
public interface GeneralConfigControllerApi {

    @PostMapping("/searchResponseResult")
    @Operation(summary = "查询系统响应结果类型")
    public GraceResult searchResponseResult(CommonLanguageBO bo);

}
