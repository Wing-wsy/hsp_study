package com.yz.api.controller.cst;

import com.yz.common.result.GraceResult;
import com.yz.model.bo.cst.SearchUserBriefInfoBO;
import com.yz.model.vo.cst.UserBriefInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试控制层2
 */
@Tag(name = "TUserController", description = "用户控制层")
@RequestMapping("/user")

public interface TUserControllerApi {

    @PostMapping("/searchUserBriefInfo")
    @Operation(summary = "查询用户基础信息")
    @ApiResponse(content = @Content(schema = @Schema(implementation = UserBriefInfoVO.class)))

    public GraceResult searchUserBriefInfo(SearchUserBriefInfoBO bo);

}
