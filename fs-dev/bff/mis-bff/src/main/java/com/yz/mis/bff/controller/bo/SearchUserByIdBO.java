package com.yz.mis.bff.controller.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Schema(description = "查询用户")
public class SearchUserByIdBO {

    @NotNull(message = "userId不能为空")
    @Min(value = 1, message = "userId不能小于1")
    @Schema(description = "用户ID")
    private Integer userId;
}
