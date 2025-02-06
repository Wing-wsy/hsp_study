package com.yz.model.bo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "修改角色BO")
public class UpdateRoleBO {

    @NotNull(message = "修改角色id 不能为空")
    @Schema(description = "角色ID")
    private Long id;

    @Schema(description = "权限集合")
    private String permissions;

    @Schema(description = "角色编码")
    private String roleCode;

    @Schema(description = "角色中文名")
    private String roleNameByZH;

    @Schema(description = "角色西语名")
    private String roleNameByES;

    @Schema(description = "描述")
    private String comment;

    @Schema(description = "1有效，0禁用")
    private Integer status;

    @Pattern(regexp = "^up$|^down$")
    @Schema(description = "移动方式，up上移 down下移")
    private String moveMode;



}
