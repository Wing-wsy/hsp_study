package com.yz.model.bo.odr;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "查询用户订单信息BO")
public class SearchOrderByUserBO {

    @NotNull(message = "用户ID不能为空")
    @Min(value = 1, message = "用户ID不能小于1")
    @Schema(description = "用户ID")
    private Long userId;
}
