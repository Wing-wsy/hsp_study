package com.yz.api.controller.mis_bff.permission;

import com.yz.common.result.GraceResult;
import com.yz.model.form.mis_bff.permission.AddMenuForm;
import com.yz.model.form.mis_bff.permission.DeleteMenuForm;
import com.yz.model.form.mis_bff.permission.MenuTreeForm;
import com.yz.model.form.mis_bff.permission.UpdateMenuForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统菜单控制层
 */
@Tag(name = "SystemMenuController", description = "系统菜单控制器")
@RequestMapping("/menu")
public interface SystemMenuControllerApi {

    @PostMapping("/menuTree")
    @Operation(summary = "查询菜单树")
    public GraceResult menuTree(MenuTreeForm bo);

    @PostMapping("/addMenu")
    @Operation(summary = "新增菜单")
    public GraceResult addMenu(AddMenuForm bo);

    @DeleteMapping("/deleteMenu")
    @Operation(summary = "删除菜单")
    public GraceResult deleteMenu(DeleteMenuForm bo);

    @PutMapping("/updateMenu")
    @Operation(summary = "修改菜单")
    public GraceResult updateMenu(UpdateMenuForm bo);


}
