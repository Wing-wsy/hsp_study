package com.yz.model.bo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
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

    @Min(value = 1, message = "sort 不能小于1")
    @Schema(description = "排序")
    private int sort;



}
