package com.yz.mis.controller;

import com.yz.api.controller.mis.SystemRoleControllerApi;
import com.yz.common.exception.GraceException;
import com.yz.common.result.GraceResult;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.common.util.StrUtils;
import com.yz.common.util.page.PageResult;
import com.yz.mis.service.TSystemRoleService;
import com.yz.model.bo.mis.DeleteRoleBO;
import com.yz.model.bo.mis.InsertRoleBO;
import com.yz.model.bo.mis.SelectRoleListBO;
import com.yz.model.bo.mis.SelectRolePermBO;
import com.yz.model.bo.mis.UpdateRoleBO;
import com.yz.model.condition.mis.TSystemRoleConditions;
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
        TSystemRoleConditions conditions = TSystemRoleConditions.newInstance()
                .addLanguage(bo.getLanguage())
                .addStatus(Integer.parseInt(bo.getStatus()))
                .addPage(bo.getPage())
                .addPageSize(bo.getPageSize());
        PageResult<SelectRoleListVO> selectRoleListVOPageResult = tSystemRoleService.selectRoleList(conditions);
        return GraceResult.ok(selectRoleListVOPageResult);
    }

    @Override
    public GraceResult selectRolePerm(@RequestBody @Valid SelectRolePermBO bo) {
        return GraceResult.ok(tSystemRoleService.selectRolePermTree(bo.getRoleCode(), Integer.parseInt(bo.getStatus()),  bo.getLanguage()));
    }

    @Override
    public GraceResult insertRole(@RequestBody @Valid InsertRoleBO bo) {
        tSystemRoleService.insertRole(bo);
        return GraceResult.ok();
    }

    @Override
    public GraceResult deleteRole(@RequestBody @Valid DeleteRoleBO bo) {
        // FIXME 删除角色功能暂不实现，如果不使用某个角色，可以通过修改接口将角色禁用
        return null;
    }

    @Override
    public GraceResult updateRole(@RequestBody @Valid UpdateRoleBO bo) {
        // 只传了其中一个，则拦截，要么都传，要么都不传
        if (StrUtils.isNotBlank(bo.getRoleNameByES()) != StrUtils.isNotBlank(bo.getRoleNameByZH())) {
            GraceException.display(ResponseStatusEnum.SYSTEM_PARAMS_SETTINGS_ERROR);
        }
        tSystemRoleService.updateRole(bo);
        return GraceResult.ok();
    }
}
