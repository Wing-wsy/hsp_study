package com.yz.api.controller.mis;


import com.yz.common.result.GraceResult;
import com.yz.model.bo.mis.SystemLoginBO;
import com.yz.model.vo.mis.SystemLoginVO;
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
@Tag(name = "AuthController", description = "系统用户权限控制层")
@RequestMapping("/auth")
public interface AuthControllerApi {

    @PostMapping("/login")
    @Operation(summary = "系统用户登录接口")
    @ApiResponse(content = @Content(schema = @Schema(implementation = SystemLoginVO.class)))
    public GraceResult login(SystemLoginBO bo);


}
