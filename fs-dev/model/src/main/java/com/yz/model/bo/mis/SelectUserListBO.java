package com.yz.model.bo.mis;

import com.yz.model.bo.common.CommonPageBO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "查询角色列表BO")
public class SelectUserListBO extends CommonPageBO {

    @Pattern(regexp = "^0$|^1$|^2$|^3$|^4$")
    @Schema(description = "状态 1有效，2休息 3离职 0禁用 4全部（不传默认4查询全部）")
    private String status;

    @Schema(description = "用户名(模糊查询)")
    private String username;

    @Schema(description = "昵称(模糊查询)")
    private String name;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "部门编码")
    private String deptCode;

    @Schema(description = "角色编码")
    private String roleCode;

}
