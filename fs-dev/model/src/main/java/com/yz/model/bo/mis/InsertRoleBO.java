package com.yz.model.bo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "新增角色BO")
public class InsertRoleBO {

    @NotBlank(message = "permissions 不能为空")
    @Schema(description = "权限集合")
    private String permissions;

    @NotBlank(message = "comment 不能为空")
    @Schema(description = "描述")
    private String comment;

    @NotBlank(message = "roleCode 不能为空")
    @Schema(description = "角色编码")
    private String roleCode;

    @NotNull(message = "status 不能为空")
    @Schema(description = "禁用状态 1有效，0禁用")
    private Integer status;

    @NotBlank(message = "roleNameByZH 不能为空")
    @Schema(description = "角色名中文")
    private String roleNameByZH;

    @NotBlank(message = "roleNameByES 不能为空")
    @Schema(description = "角色名西语")
    private String roleNameByES;
}
