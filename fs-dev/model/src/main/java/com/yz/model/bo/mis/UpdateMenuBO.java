package com.yz.model.bo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "修改菜单BO")
public class UpdateMenuBO {

    @NotNull(message = "修改菜单id 不能为空")
    @Schema(description = "修改菜单ID")
    private Long id;

    @Schema(description = "菜单中文名")
    private String menuNameByZH;

    @Schema(description = "菜单西语名")
    private String menuNameByES;

    @Schema(description = "菜单编码")
    private String menuCode;

    @Schema(description = "1有效，0禁用")
    private Integer status;

    @Schema(description = "排序(修改排序和其他属性不支持同时修改)")
    private int sort;



}
