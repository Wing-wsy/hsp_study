package com.yz.model.bo.mis;

import com.yz.model.bo.common.CommonPageBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "查询系统响应结果类型BO")
public class SelectResponseResultBO extends CommonPageBO {

    @Schema(description = "code响应编码(精确查询)")
    private String code;

    @Schema(description = "msg提示内容(模糊查询)")
    private String msg;
}
