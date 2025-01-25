package com.yz.model.bo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "删除系统响应结果类型BO")
public class DeleteResponseResultBO {

    @NotNull(message = "ID 不能为空")
    @Schema(description = "删除记录ID")
    private Long id;
}
