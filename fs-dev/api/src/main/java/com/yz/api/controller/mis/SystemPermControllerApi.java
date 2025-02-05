package com.yz.api.controller.mis;

import com.yz.common.result.GraceResult;
import com.yz.model.bo.mis.PermTreeBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统权限控制层
 */
@Tag(name = "SystemPermController", description = "系统权限控制层")
@RequestMapping("/perm")
public interface SystemPermControllerApi {

    @PostMapping("/permTree")
    @Operation(summary = "查询权限树")
    public GraceResult permTree(PermTreeBO bo);

}
