package com.yz.api.controller.mis;

import com.yz.common.result.GraceResult;
import com.yz.model.bo.mis.AddPermBO;
import com.yz.model.bo.mis.DeletePermBO;
import com.yz.model.bo.mis.PermTreeBO;
import com.yz.model.bo.mis.UpdatePermBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统权限控制层
 */
@Tag(name = "SystemPermController", description = "系统权限控制层")
@RequestMapping("/perm")
public interface SystemPermControllerApi {

    @PostMapping("/permTree")
    @Operation(summary = "查询权限树")
    public GraceResult permTree(PermTreeBO bo);

    @PostMapping("/addPerm")
    @Operation(summary = "新增权限")
    public GraceResult addPerm(AddPermBO bo);

    @DeleteMapping("/deletePerm")
    @Operation(summary = "删除权限")
    public GraceResult deletePerm(DeletePermBO bo);

    @PutMapping("/updatePerm")
    @Operation(summary = "修改权限")
    public GraceResult updatePerm(UpdatePermBO bo);


}
