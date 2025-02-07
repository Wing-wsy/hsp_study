package com.yz.model.bo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "删除部门BO")
public class DeleteDeptBO {

    @NotNull(message = "删除部门id 不能为空")
    @Schema(description = "删除部门ID")
    private Long id;


}
