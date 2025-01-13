package com.yz.model.bo.cst;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "测试接口BO")
public class TestBO {

    @Schema(description = "名称")
    private String name;

    @Schema(description = "授权码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mobile;
}
