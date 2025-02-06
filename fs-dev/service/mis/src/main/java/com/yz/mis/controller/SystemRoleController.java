package com.yz.mis.controller;

import com.yz.api.controller.mis.SystemRoleControllerApi;
import com.yz.common.result.GraceResult;
import com.yz.common.util.page.PageResult;
import com.yz.mis.service.TSystemRoleService;
import com.yz.model.bo.mis.SelectRoleListBO;
import com.yz.model.bo.mis.SelectRolePermBO;
import com.yz.model.vo.mis.SelectRoleListVO;
import com.yz.service.base.controller.BaseController;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class SystemRoleController extends BaseController implements SystemRoleControllerApi {

    @Resource
    private TSystemRoleService tSystemRoleService;

    @Override
    public GraceResult selectRoleList(@RequestBody @Valid SelectRoleListBO bo) {
        PageResult<SelectRoleListVO> selectRoleListVOPageResult
                = tSystemRoleService.selectRoleList(bo.getLanguage(), Integer.parseInt(bo.getStatus()), bo.getPage(), bo.getPageSize());
        return GraceResult.ok(selectRoleListVOPageResult);
    }

    @Override
    public GraceResult selectRolePerm(@RequestBody @Valid SelectRolePermBO bo) {
        return GraceResult.ok(tSystemRoleService.selectRolePermTree(bo.getRoleCode(), Integer.parseInt(bo.getStatus()),  bo.getLanguage()));
    }
}
