package com.yz.model.bo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "新增权限BO")
public class AddPermBO {

    @NotBlank(message = "menuCode 不能为空")
    @Schema(description = "菜单编码")
    private String menuCode;

    @NotBlank(message = "permName 不能为空")
    @Schema(description = "权限名称")
    private String permName;

}
