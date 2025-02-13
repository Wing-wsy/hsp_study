package com.yz.mis.controller;


import com.yz.api.controller.mis.AuthControllerApi;
import com.yz.common.exception.GraceException;
import com.yz.common.result.GraceResult;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.common.util.BeanUtils;
import com.yz.common.util.ObjectUtils;
import com.yz.mis.service.custom.AuthService;
import com.yz.mis.service.TSystemUserService;
import com.yz.model.bo.mis.SystemLoginBO;
import com.yz.model.dto.mis.LoginDTO;
import com.yz.model.vo.mis.SystemLoginVO;
import com.yz.service.base.controller.BaseController;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@RestController
public class AuthController extends BaseController implements AuthControllerApi {

    @Resource
    private AuthService authService;
    @Resource
    private TSystemUserService TSystemUserService;

    @Override
    public GraceResult login(@RequestBody @Valid SystemLoginBO bo) {
        LoginDTO loginDTO = BeanUtils.toBean(bo, LoginDTO.class);
        Long systemUserId = authService.login(loginDTO);
        // 1. 判断是否验证通过
        if (ObjectUtils.isNull(systemUserId)) {
            GraceException.display(ResponseStatusEnum.ADMIN_LOGIN_ERROR);
        }

        // 2. 验证通过获取账号权限
        SystemLoginVO vo = new SystemLoginVO();
        vo.setSystemUserId(systemUserId);
        // 2.1 获取用户权限
        Set<String> permissions = TSystemUserService.searchUserPermissions(systemUserId);
        vo.setPermissions(permissions);

        // 2.2 获取用户权限菜单
        Set<String> permissionsMenus = TSystemUserService.searchUserPermissionsMenus(systemUserId);
        System.out.println(permissionsMenus);

        // 2.3 获取用户菜单展示
        Set<String> menus = TSystemUserService.searchUserMenus(systemUserId);
        vo.setMenus(menus);
        return GraceResult.ok(vo);
    }
}
