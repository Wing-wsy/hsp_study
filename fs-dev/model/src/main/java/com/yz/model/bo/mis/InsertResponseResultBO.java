package com.yz.model.bo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "新增系统响应结果类型BO")
public class InsertResponseResultBO {

    @NotBlank(message = "code 不能为空")
    @Schema(description = "响应编码")
    private String code;

    @NotNull(message = "status 不能为空")
    @Schema(description = "响应状态")
    private Integer status;

    @NotBlank(message = "msgByZH 不能为空")
    @Schema(description = "错误提示内容中文")
    private String msgByZH;

    @NotBlank(message = "msgByES 不能为空")
    @Schema(description = "错误提示内容西语")
    private String msgByES;
}
