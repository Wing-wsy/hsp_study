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
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试控制层2
 */
@Tag(name = "系统响应结果类型控制层", description = "系统返回的错误编码")
@RequestMapping("/result")
public interface ResponseResultControllerApi {

    @PostMapping("/searchResponseResult")
    @Operation(summary = "查询系统响应结果类型")
//    @ApiResponse(content = @Content(schema = @Schema(implementation = UserBriefInfoVO.class)))
    public GraceResult searchResponseResult();

}
