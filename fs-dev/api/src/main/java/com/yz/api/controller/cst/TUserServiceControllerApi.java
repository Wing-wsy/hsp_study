package com.yz.api.controller.cst;

import com.yz.common.result.GraceResult;
import com.yz.model.bo.cst.SearchUserBriefInfoBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "TUserServiceController", description = "用户接口")
@RequestMapping("/user")
public interface TUserServiceControllerApi {

    @PostMapping("/searchUserBriefInfo")
    @Operation(summary = "查询用户基础信息")
    public GraceResult searchUserBriefInfo(@RequestBody @Valid SearchUserBriefInfoBO bo);
}
