package com.yz.model.bo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "删除权限BO")
public class DeletePermBO {

    @NotNull(message = "删除权限id 不能为空")
    @Schema(description = "删除权限ID")
    private Long id;


}
