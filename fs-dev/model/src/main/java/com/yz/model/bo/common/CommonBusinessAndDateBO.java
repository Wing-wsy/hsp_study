package com.yz.model.bo.common;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "通用业务&通用日期范围BO")
public class CommonBusinessAndDateBO extends CommonLanguageBO{

    @NotNull(message = "系统编码不能为空")
    @Schema(description = "系统编码")
    private String itemCode;

    @NotNull(message = "开始时间不能为空")
    @Schema(description = "开始时间")
    private LocalDateTime startDate;

    @NotNull(message = "结束时间不能为空")
    @Schema(description = "结束时间")
    private LocalDateTime endDate;

}
