package com.yz.model.bo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
@Schema(description = "新增用户BO")
public class InsertUserBO {

    @NotBlank(message = "username 不能为空")
    @Schema(description = "用户名")
    private String username;

    @NotBlank(message = "name 不能为空")
    @Schema(description = "昵称")
    private String name;

    @NotBlank(message = "password 不能为空")
    @Schema(description = "密码（加密）")
    private String password;

    @NotNull(message = "sex 不能为空")
    @Pattern(regexp = "^0$|^1$|^2$")
    @Schema(description = "性别 1男 2女 0未知")
    private String sex;

    @NotBlank(message = "mobile 不能为空")
    @Schema(description = "电话")
    private String mobile;

    @NotBlank(message = "email 不能为空")
    @Email(message = "email内容不正确")
    @Schema(description = "邮箱")
    private String email;

    @NotBlank(message = "role 不能为空")
    @Schema(description = "角色集合")
    private String role;

    @NotNull(message = "root 不能为空")
    @Pattern(regexp = "^0$|^1$")
    @Schema(description = "是否为超级管理员")
    private String root;

    @NotBlank(message = "deptCode 不能为空")
    @Schema(description = "部门编码")
    private String deptCode;

    @NotNull(message = "status 不能为空")
    @Pattern(regexp = "^0$|^1$|^2$|^3$")
    @Schema(description = "1有效，2休息 3离职 0禁用")
    private String status;


}