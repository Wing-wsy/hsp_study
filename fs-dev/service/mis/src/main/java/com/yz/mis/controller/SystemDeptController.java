package com.yz.mis.controller;


import com.yz.api.controller.mis.SystemDeptControllerApi;
import com.yz.common.result.GraceResult;
import com.yz.mis.service.TSystemDeptService;
import com.yz.model.bo.mis.AddDeptBO;
import com.yz.service.base.controller.BaseController;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class SystemDeptController extends BaseController implements SystemDeptControllerApi {

    @Resource
    private TSystemDeptService tSystemDeptService;

    @Override
    public GraceResult addDept(@RequestBody @Valid AddDeptBO bo) {
        tSystemDeptService.addDept(bo);
        return GraceResult.ok();
    }
}
