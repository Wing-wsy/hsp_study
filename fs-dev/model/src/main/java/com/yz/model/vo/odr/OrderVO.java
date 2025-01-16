package com.yz.model.vo.odr;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yz.common.constant.Times;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "查询订单信息VO")
public class OrderVO {

    @Schema(description = "订单ID")
    private Long orderId;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "订单状态")
    private Integer status;

    @Schema(description = "到手金额")
    private BigDecimal actualAmounts;

    // 如果不加@JsonFormat，前端拿到的日期格式是：2025-01-18T10:43:09，加了之后格式是：2025-01-18 10:43:09
    @JsonFormat(pattern = Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS)
    @Schema(description = "到期日期")
    private LocalDateTime repayDate;
}
