package com.yz.model.bo.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "通用分页BO")
public class CommonPageBO extends CommonLanguageBO{

    @NotNull(message = "page不能为空")
    @Min(value = 1, message = "page不能小于1")
    @Schema(description = "第几页")
    private Integer page;

    @NotNull(message = "pageSize不能为空")
    @Min(value = 1, message = "pageSize不能小于1")
    @Schema(description = "每页条数")
    private Integer pageSize;
}
