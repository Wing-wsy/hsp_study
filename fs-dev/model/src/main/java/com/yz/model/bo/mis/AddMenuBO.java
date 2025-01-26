package com.yz.model.bo.mis;

import com.yz.model.bo.common.CommonLanguageBO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "新增菜单BO")
public class AddMenuBO {

    @NotNull(message = "level 不能为空")
    @Pattern(regexp = "^1$|^2$")
    @Schema(description = "1顶级菜单 2子级菜单")
    private String level;

    @NotBlank(message = "menuNameByZH 不能为空")
    @Schema(description = "菜单中文名")
    private String menuNameByZH;

    @NotBlank(message = "menuNameByES 不能为空")
    @Schema(description = "菜单西语名")
    private String menuNameByES;

    @NotBlank(message = "menuCode 不能为空")
    @Schema(description = "菜单编码")
    private String menuCode;

//    @NotNull(message = "fatherMenuCode 不能为空")
    @Schema(description = "父级菜单编码，添加顶级菜单不用传")
    private String fatherMenuCode;

    @NotNull(message = "sort 不能为空")
    @Min(value = 1, message = "sort 不能小于1")
    @Schema(description = "排序")
    private int sort;



}
