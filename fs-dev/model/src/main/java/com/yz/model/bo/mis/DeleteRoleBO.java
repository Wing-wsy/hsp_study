package com.yz.model.bo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "删除角色BO")
public class DeleteRoleBO {

    @NotNull(message = "删除角色id 不能为空")
    @Schema(description = "删除角色ID")
    private Long id;


}
