package com.yz.model.bo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "新增部门BO")
public class AddDeptBO {

    @NotBlank(message = "deptNameByZH 不能为空")
    @Schema(description = "部门中文名")
    private String deptNameByZH;

    @NotBlank(message = "deptNameByES 不能为空")
    @Schema(description = "部门西语名")
    private String deptNameByES;

    @NotBlank(message = "deptCode 不能为空")
    @Schema(description = "部门编码")
    private String deptCode;

    @Schema(description = "父级部门主键")
    private Long parentId;

    @Schema(description = "部门电话")
    private String mobile;

    @Schema(description = "部门邮箱")
    private String email;

    @Schema(description = "描述")
    private String comment;


}
