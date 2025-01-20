package com.yz.model.bo.common;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "通用业务参数BO")
public class CommonLanguageBO {

    @NotNull(message = "语言编码不能为空")
    @Schema(description = "语言编码")
    private String language;
}
