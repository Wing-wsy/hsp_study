package com.yz.api.controller.mis_bff;

import com.yz.common.result.GraceResult;
import com.yz.model.from.mis_bff.SearchUserBriefInfoFrom;
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
    @Operation(summary = "根据ID查找用户")
    @ApiResponse(content = @Content(schema = @Schema(implementation = SearchUserBriefInfoRes.class)))
    public GraceResult searchUserBriefInfo(SearchUserBriefInfoFrom from);
}
