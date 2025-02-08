package com.yz.model.bo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "删除用户BO")
public class DeleteUserBO {

    @NotNull(message = "删除用户id 不能为空")
    @Schema(description = "删除用户ID")
    private Long id;


}
