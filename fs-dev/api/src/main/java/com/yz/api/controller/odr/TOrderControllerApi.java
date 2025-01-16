package com.yz.api.controller.odr;

import com.yz.common.result.GraceResult;
import com.yz.model.bo.cst.SearchUserBriefInfoBO;
import com.yz.model.bo.odr.SearchOrderBO;
import com.yz.model.vo.cst.SearchUserBriefInfoVO;
import com.yz.model.vo.odr.SearchOrderVO;
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
@Tag(name = "TOrderController", description = "订单接口")
@RequestMapping("/order")
public interface TOrderControllerApi {

    @PostMapping("/searchOrder")
    @Operation(summary = "查询订单信息")
    @ApiResponse(content = @Content(schema = @Schema(implementation = SearchOrderVO.class)))
    public GraceResult searchOrder(@RequestBody @Valid SearchOrderBO bo);
}
