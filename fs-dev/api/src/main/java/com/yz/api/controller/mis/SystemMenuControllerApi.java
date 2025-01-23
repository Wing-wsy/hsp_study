package com.yz.api.controller.mis;


import com.yz.common.result.GraceResult;
import com.yz.model.bo.common.CommonLanguageBO;
import com.yz.model.bo.mis.AddMenuBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试控制层2
 */
@Tag(name = "SystemMenuControllerApi", description = "系统菜单接口")
@RequestMapping("/menu")
public interface SystemMenuControllerApi {

    @PostMapping("/menuTree")
    @Operation(summary = "查询菜单树")
    public GraceResult menuTree(CommonLanguageBO bo);

    @PostMapping("/addMenu")
    @Operation(summary = "新增菜单")
    public GraceResult addMenu(AddMenuBO bo);


}
