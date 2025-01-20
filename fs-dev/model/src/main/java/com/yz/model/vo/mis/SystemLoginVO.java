package com.yz.model.vo.mis;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "系统登录VO")
public class SystemLoginVO {

    @Schema(description = "系统用户ID")
    private Long systemUserId;

    @Schema(description = "系统用户权限列表")
    private Set<String> permissions;

    @Schema(description = "系统用户菜单列表")
    private Set<String> menus;

}
