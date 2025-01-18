package com.yz.model.vo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "系统登录VO")
public class SystemLoginVO {

    @Schema(description = "登录状态 成功true 失败false")
    private boolean loginStatus;

}
