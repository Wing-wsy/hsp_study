package com.yz.model.bo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "删除菜单BO")
public class DeleteMenuBO {

    @NotNull(message = "删除菜单id 不能为空")
    @Schema(description = "删除菜单ID")
    private Long id;


}
