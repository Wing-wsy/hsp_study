package com.yz.model.form.mis_bff.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "删除菜单BO")
public class DeleteMenuForm {

    @NotNull(message = "删除菜单id 不能为空")
    @Schema(description = "删除菜单ID")
    private Long id;


}
