package com.yz.model.bo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "系统登录BO")
public class SystemLoginBO {

    @NotNull(message = "用户名不能为空")
    @Schema(description = "用户名")
    private String userName;

    @NotNull(message = "密码不能为空")
    @Schema(description = "密码")
    private String password;
}
