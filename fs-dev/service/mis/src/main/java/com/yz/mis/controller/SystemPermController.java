package com.yz.mis.controller;

import com.yz.api.controller.mis.SystemPermControllerApi;
import com.yz.common.result.GraceResult;
import com.yz.mis.service.TSystemPermService;
import com.yz.model.bo.mis.PermTreeBO;
import com.yz.service.base.controller.BaseController;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class SystemPermController extends BaseController implements SystemPermControllerApi {
    @Resource
    private TSystemPermService tSystemPermService;

    @Override
    public GraceResult permTree(@RequestBody @Valid PermTreeBO bo) {
        return GraceResult.ok(tSystemPermService.permTree(bo.getLanguage(), Integer.parseInt(bo.getStatus())));
    }


//
//    @Override
//    public GraceResult addMenu(@RequestBody @Valid AddMenuBO bo) {
//        tSystemMenuService.addMenu(bo);
//        return GraceResult.ok();
//    }
//
//    @Override
//    public GraceResult deleteMenu(@RequestBody @Valid DeleteMenuBO bo) {
//        tSystemMenuService.deleteMenu(bo.getId());
//        return GraceResult.ok();
//    }
//
//    @Override
//    public GraceResult updateMenu(@RequestBody @Valid UpdateMenuBO bo) {
//        tSystemMenuService.updateMenu(bo);
//        return GraceResult.ok();
//    }
}
