package com.yz.mis.bff.controller.permission;

import com.yz.api.controller.mis_bff.permission.SystemMenuControllerApi;
import com.yz.common.result.GraceResult;
import com.yz.mis.bff.service.permission.SystemMenuService;
import com.yz.model.form.mis_bff.permission.AddMenuForm;
import com.yz.model.form.mis_bff.permission.DeleteMenuForm;
import com.yz.model.form.mis_bff.permission.MenuTreeForm;
import com.yz.model.form.mis_bff.permission.UpdateMenuForm;
import com.yz.model.res.mis_bff.permission.MenuTreeRes;
import com.yz.service.base.controller.BaseController;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class SystemMenuController extends BaseController implements SystemMenuControllerApi {

    @Resource
    private SystemMenuService systemMenuService;

    @Override
    public GraceResult menuTree(@RequestBody @Valid MenuTreeForm form) {
        MenuTreeRes menuTreeRes = systemMenuService.menuTree(form);
        return GraceResult.ok(menuTreeRes);
    }

    @Override
    public GraceResult addMenu(@RequestBody @Valid AddMenuForm form) {
        systemMenuService.addMenu(form);
        return GraceResult.ok();
    }

    @Override
    public GraceResult deleteMenu(@RequestBody @Valid DeleteMenuForm form) {
        systemMenuService.deleteMenu(form);
        return GraceResult.ok();
    }

    @Override
    public GraceResult updateMenu(@RequestBody @Valid UpdateMenuForm form) {
        systemMenuService.updateMenu(form);
        return GraceResult.ok();
    }
}
