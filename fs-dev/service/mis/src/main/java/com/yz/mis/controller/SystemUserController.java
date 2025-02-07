package com.yz.mis.controller;


import com.yz.api.controller.mis.SystemUserControllerApi;
import com.yz.common.constant.Basic;
import com.yz.common.result.GraceResult;
import com.yz.common.util.page.PageResult;
import com.yz.mis.service.TSystemUserService;
import com.yz.model.bo.mis.SelectUserListBO;
import com.yz.model.condition.mis.TSystemRoleConditions;
import com.yz.model.condition.mis.TSystemUserConditions;
import com.yz.model.vo.mis.SelectRoleListVO;
import com.yz.model.vo.mis.SelectUserListVO;
import com.yz.service.base.controller.BaseController;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class SystemUserController extends BaseController implements SystemUserControllerApi {

    @Resource
    private TSystemUserService tSystemUserService;


    @Override
    public GraceResult selectUserList(@RequestBody @Valid SelectUserListBO bo) {
        Integer status = bo.getStatus() != null && !Basic.FOUR_STR.equals(bo.getStatus()) ? Integer.parseInt(bo.getStatus()) : null;
        TSystemUserConditions conditions = TSystemUserConditions.newInstance()
                .addStatus(status)
                .addUsername(bo.getUsername())
                .addLanguage(bo.getLanguage())
                .addName(bo.getName())
                .addMobile(bo.getMobile())
                .addDeptCode(bo.getDeptCode())
                .addRoleCode(bo.getRoleCode())
                .addPage(bo.getPage())
                .addPageSize(bo.getPageSize());
        PageResult<SelectUserListVO> selectUserListVOPageResult = tSystemUserService.selectUserList(conditions);
        return GraceResult.ok(selectUserListVOPageResult);
    }
}
