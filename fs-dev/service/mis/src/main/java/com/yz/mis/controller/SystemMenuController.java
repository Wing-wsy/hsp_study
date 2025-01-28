package com.yz.mis.controller;

import com.yz.api.controller.mis.SystemMenuControllerApi;
import com.yz.common.result.GraceResult;
import com.yz.mis.service.TSystemMenuService;
import com.yz.model.bo.common.CommonLanguageBO;
import com.yz.model.bo.mis.AddMenuBO;
import com.yz.model.bo.mis.DeleteMenuBO;
import com.yz.model.bo.mis.MenuTreeBO;
import com.yz.model.bo.mis.UpdateMenuBO;
import com.yz.service.base.controller.BaseController;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制层2
 */
@Slf4j
@RestController
public class SystemMenuController extends BaseController implements SystemMenuControllerApi {

    @Resource
    private TSystemMenuService tSystemMenuService;

    @Override
    public GraceResult menuTree(@RequestBody @Valid MenuTreeBO bo) {
        return GraceResult.ok(tSystemMenuService.menuTree(bo.getLanguage(), bo.getStatus()));
    }

    @Override
    public GraceResult addMenu(@RequestBody @Valid AddMenuBO bo) {
        tSystemMenuService.addMenu(bo);
        return GraceResult.ok();
    }

    @Override
    public GraceResult deleteMenu(@RequestBody @Valid DeleteMenuBO bo) {
        tSystemMenuService.deleteMenu(bo.getId());
        return GraceResult.ok();
    }

    @Override
    public GraceResult updateMenu(@RequestBody @Valid UpdateMenuBO bo) {
        tSystemMenuService.updateMenu(bo);
        return GraceResult.ok();
    }
}
