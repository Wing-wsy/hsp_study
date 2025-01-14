package com.yz.model.bo.cst;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "测试接口BO")
public class TestBO {

    @Schema(description = "名称")
    private String name;

    @Schema(description = "授权码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

//    @NotNull(message = "手机号不能为空")
    @NotBlank(message = "手机号不能为空")
    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mobile;


    private Integer age;

}
