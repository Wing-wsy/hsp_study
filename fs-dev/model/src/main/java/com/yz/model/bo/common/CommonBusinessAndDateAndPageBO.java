package com.yz.model.bo.common;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "通用业务&通用日期范围&通用分页BO")
public class CommonBusinessAndDateAndPageBO extends CommonLanguageBO{

    @NotNull(message = "系统编码不能为空")
    @Schema(description = "系统编码")
    private String itemCode;

    @NotNull(message = "开始时间不能为空")
    @Schema(description = "开始时间")
    private LocalDateTime startDate;

    @NotNull(message = "结束时间不能为空")
    @Schema(description = "结束时间")
    private LocalDateTime endDate;

    @NotNull(message = "page不能为空")
    @Min(value = 1, message = "page不能小于1")
    @Schema(description = "第几页")
    private Integer page;

    @NotNull(message = "pageSize不能为空")
    @Min(value = 1, message = "pageSize不能小于1")
    @Schema(description = "每页条数")
    private Integer pageSize;

}
