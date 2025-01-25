package com.yz.model.bo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "修改系统响应结果类型BO")
public class UpdateResponseResultBO {

    @NotNull(message = "ID 不能为空")
    @Schema(description = "修改记录ID")
    private Long id;

    @Schema(description = "响应编码")
    private String code;

    @Schema(description = "响应状态")
    private Integer status;

    @Schema(description = "错误提示内容中文 msgByZH传了msgByES必传")
    private String msgByZH;

    @Schema(description = "错误提示内容西语 msgByES传了msgByZH必传")
    private String msgByES;
}
