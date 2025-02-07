package com.yz.model.bo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "修改部门BO")
public class UpdateDeptBO {

    @NotNull(message = "修改部门id 不能为空")
    @Schema(description = "修改部门ID")
    private Long id;

    @Schema(description = "部门中文名")
    private String deptNameByZH;

    @Schema(description = "部门西语名")
    private String deptNameByES;

    @Schema(description = "部门编码")
    private String deptCode;

    @Schema(description = "部门电话")
    private String mobile;

    @Schema(description = "部门邮箱")
    private String email;

    @Schema(description = "描述")
    private String comment;

    @Schema(description = "1有效，0禁用")
    private Integer status;

    @Pattern(regexp = "^up$|^down$")
    @Schema(description = "移动方式，up上移 down下移")
    private String moveMode;

}
