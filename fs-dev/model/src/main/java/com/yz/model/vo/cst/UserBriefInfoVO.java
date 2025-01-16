package com.yz.model.vo.cst;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "查询用户基础信息VO")
public class UserBriefInfoVO {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户名称")
    private String name;

}
