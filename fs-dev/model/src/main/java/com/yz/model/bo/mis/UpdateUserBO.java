package com.yz.model.bo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
@Schema(description = "修改用户BO")
public class UpdateUserBO {

    @NotNull(message = "修改用户id 不能为空")
    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "昵称")
    private String name;

    @Schema(description = "密码（加密）")
    private String password;

    @Pattern(regexp = "^0$|^1$|^2$")
    @Schema(description = "性别 1男 2女 0未知")
    private String sex;

    @Schema(description = "电话")
    private String mobile;

    @Email(message = "email内容不正确")
    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "角色集合")
    private String role;

    @Pattern(regexp = "^0$|^1$")
    @Schema(description = "是否为超级管理员")
    private String root;

    @Schema(description = "部门编码")
    private String deptCode;

    @Pattern(regexp = "^0$|^1$|^2$|^3$")
    @Schema(description = "1有效，2休息 3离职 0禁用")
    private String status;


}