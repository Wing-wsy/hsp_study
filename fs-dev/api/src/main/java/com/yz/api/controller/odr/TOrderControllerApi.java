package com.yz.api.controller.odr;

import com.yz.common.result.GraceResult;
import com.yz.model.bo.odr.SearchOrderByOrderIdBO;
import com.yz.model.bo.odr.SearchOrderByUserBO;
import com.yz.model.vo.odr.OrderVO;
import com.yz.model.vo.odr.OrderListVO;
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
@Tag(name = "TOrderController", description = "订单接口")
@RequestMapping("/order")
public interface TOrderControllerApi {

    @PostMapping("/searchOrderByOrderId")
    @Operation(summary = "根据订单ID查询订单信息")
    @ApiResponse(content = @Content(schema = @Schema(implementation = OrderVO.class)))
    public GraceResult searchOrderByOrderId(SearchOrderByOrderIdBO bo);

    @PostMapping("/searchOrderByUser")
    @Operation(summary = "根据用户ID查询用户订单信息")
    @ApiResponse(content = @Content(schema = @Schema(implementation = OrderListVO.class)))
    public GraceResult searchOrderByUser(SearchOrderByUserBO bo);
}
