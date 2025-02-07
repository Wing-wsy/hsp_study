package com.yz.mis.controller;


import com.yz.api.controller.mis.SystemDeptControllerApi;
import com.yz.common.result.GraceResult;
import com.yz.common.util.page.PageResult;
import com.yz.mis.service.TSystemDeptService;
import com.yz.model.bo.mis.AddDeptBO;
import com.yz.model.bo.mis.DeleteDeptBO;
import com.yz.model.bo.mis.SelectDeptListBO;
import com.yz.model.vo.mis.SelectDeptListVO;
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

    @Override
    public GraceResult deleteDept(@RequestBody @Valid DeleteDeptBO bo) {
        tSystemDeptService.deleteDept(bo.getId());
        return GraceResult.ok();
    }

    // TODO wing
    @Override
    public GraceResult selectDeptList(@RequestBody @Valid SelectDeptListBO bo) {
        PageResult<SelectDeptListVO> selectDeptListVOPageResult
                = tSystemDeptService.selectDeptList(bo.getLanguage(), Integer.parseInt(bo.getStatus()), bo.getPage(), bo.getPageSize());
        return GraceResult.ok(selectDeptListVOPageResult);
    }
}
