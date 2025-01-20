package com.yz.model.bo.common;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "通用业务BO")
public class CommonBusinessBO extends CommonLanguageBO{

    @NotNull(message = "系统编码不能为空")
    @Schema(description = "系统编码")
    private String itemCode;

}
