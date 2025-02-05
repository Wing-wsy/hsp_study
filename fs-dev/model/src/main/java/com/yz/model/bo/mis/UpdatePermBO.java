package com.yz.model.bo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "修改菜单BO")
public class UpdatePermBO {

    @NotNull(message = "修改权限id 不能为空")
    @Schema(description = "权限ID")
    private Long id;

    @Schema(description = "菜单编码")
    private String menuCode;

    @Schema(description = "权限名称")
    private String permissionName;

    @Schema(description = "1有效，0禁用")
    private Integer status;

    @Pattern(regexp = "^up$|^down$")
    @Schema(description = "移动方式，up上移 down下移")
    private String moveMode;



}
