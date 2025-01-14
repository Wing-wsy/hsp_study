package com.yz.api.controller.cst;

import com.yz.common.result.GraceResult;
import com.yz.model.bo.cst.SearchUserBriefInfoBO;
import com.yz.model.vo.cst.SearchUserBriefInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试控制层2
 */
@Tag(name = "TUserServiceController", description = "用户接口")
@RequestMapping("/user")
public interface TUserServiceControllerApi {

    @PostMapping("/searchUserBriefInfo")
    @Operation(summary = "查询用户基础信息")
    @ApiResponse(content = @Content(schema = @Schema(implementation = SearchUserBriefInfoVO.class)))
    public GraceResult searchUserBriefInfo(@RequestBody @Valid SearchUserBriefInfoBO bo);
}
