package com.yz.api.controller.mis_bff;

import com.yz.common.result.GraceResult;
import com.yz.model.from.mis_bff.SearchOrderAndUserBriefInfoFrom;
import com.yz.model.from.mis_bff.SearchUserBriefInfoFrom;
import com.yz.model.res.mis_bff.SearchOrderAndUserBriefInfoRes;
import com.yz.model.res.mis_bff.SearchUserBriefInfoRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/customer")
@Tag(name = "CustomerController", description = "用户Web接口")
public interface CustomerControllerApi {

    @PostMapping("/searchUserBriefInfo")
    @Operation(summary = "根据用户ID查找用户信息")
    @ApiResponse(content = @Content(schema = @Schema(implementation = SearchUserBriefInfoRes.class)))
    public GraceResult searchUserBriefInfo(SearchUserBriefInfoFrom from);

    @PostMapping("/searchOrderAndUserBriefInfo")
    @Operation(summary = "根据用户ID查找用户和订单信息")
    @ApiResponse(content = @Content(schema = @Schema(implementation = SearchOrderAndUserBriefInfoRes.class)))
    public GraceResult searchOrderAndUserBriefInfo(SearchOrderAndUserBriefInfoFrom from);
}
