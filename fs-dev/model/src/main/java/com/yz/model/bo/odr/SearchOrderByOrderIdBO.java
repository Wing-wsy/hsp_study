package com.yz.model.bo.odr;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "查询订单信息BO")
public class SearchOrderByOrderIdBO {

    @NotNull(message = "订单ID不能为空")
    @Min(value = 1, message = "订单ID不能小于1")
    @Schema(description = "订单ID")
    private Long orderId;
}
