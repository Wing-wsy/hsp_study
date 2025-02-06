package com.yz.api.controller.mis;

import com.yz.common.result.GraceResult;
import com.yz.model.bo.mis.InsertRoleBO;
import com.yz.model.bo.mis.SelectRoleListBO;
import com.yz.model.bo.mis.SelectRolePermBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统角色控制层
 */
@Tag(name = "SystemRoleController", description = "系统角色控制层")
@RequestMapping("/role")
public interface SystemRoleControllerApi {

    @PostMapping("/selectRoleList")
    @Operation(summary = "查询角色列表")
    public GraceResult selectRoleList(SelectRoleListBO bo);

    @PostMapping("/selectRolePerm")
    @Operation(summary = "查询角色权限")
    public GraceResult selectRolePerm(SelectRolePermBO bo);

    @PostMapping("/insertRole")
    @Operation(summary = "新增角色")
    public GraceResult insertRole(InsertRoleBO bo);


}
