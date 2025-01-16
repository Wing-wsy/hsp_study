package com.yz.model.dto.odr;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDTO {

    private Long orderId;

    private Long userId;

    private Integer status;

    private BigDecimal actualAmounts;

    private LocalDateTime repayDate;

    private LocalDateTime sellDate;
}
