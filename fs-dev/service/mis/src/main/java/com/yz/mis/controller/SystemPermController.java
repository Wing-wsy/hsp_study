package com.yz.mis.controller;

import com.yz.api.controller.mis.SystemPermControllerApi;
import com.yz.common.exception.GraceException;
import com.yz.common.result.GraceResult;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.common.util.StrUtils;
import com.yz.mis.service.TSystemPermService;
import com.yz.model.bo.mis.AddPermBO;
import com.yz.model.bo.mis.DeletePermBO;
import com.yz.model.bo.mis.PermTreeBO;
import com.yz.model.bo.mis.UpdatePermBO;
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

    @Override
    public GraceResult addPerm(@RequestBody @Valid AddPermBO bo) {
        tSystemPermService.addPerm(bo);
        return GraceResult.ok();
    }

    @Override
    public GraceResult deletePerm(@RequestBody @Valid DeletePermBO bo) {
        tSystemPermService.deletePerm(bo.getId());
        return GraceResult.ok();
    }

    @Override
    public GraceResult updatePerm(UpdatePermBO bo) {
        // 只传了其中一个，则拦截，要么都传，要么都不传
        if (StrUtils.isNotBlank(bo.getPermissionName()) != StrUtils.isNotBlank(bo.getMenuCode())) {
            GraceException.display(ResponseStatusEnum.SYSTEM_PARAMS_SETTINGS_ERROR);
        }
        tSystemPermService.updatePerm(bo);
        return GraceResult.ok();
    }


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
