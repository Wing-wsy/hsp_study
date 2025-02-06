package com.yz.model.bo.mis;

import com.yz.model.bo.common.CommonLanguageBO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "查询角色列表BO")
public class SelectRolePermBO extends CommonLanguageBO {

    @NotNull(message = "roleCode不能为空")
    @Schema(description = "角色编码")
    private String roleCode;

    @NotNull(message = "状态不能为空")
    @Pattern(regexp = "^1$|^2$")
    @Schema(description = "状态 1查询有效，2查询有效和禁用")
    private String status;


}
