package com.yz.mis.controller;


import com.yz.api.controller.mis.AuthControllerApi;
import com.yz.common.result.GraceResult;
import com.yz.common.util.BeanUtils;
import com.yz.common.util.ObjectUtils;
import com.yz.common.util.StrUtils;
import com.yz.mis.service.AuthService;
import com.yz.mis.service.SystemUserService;
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
    private SystemUserService systemUserService;

    @Override
    public GraceResult login(@RequestBody @Valid SystemLoginBO bo) {
        LoginDTO loginDTO = BeanUtils.toBean(bo, LoginDTO.class);
        Long systemUserId = authService.login(loginDTO);
        SystemLoginVO vo = new SystemLoginVO();
        vo.setSystemUserId(systemUserId);
        if (ObjectUtils.isNotNull(systemUserId)) {
            // 登录成功
            // 获取用户权限
            Set<String> permissions = systemUserService.searchUserPermissions(systemUserId);
            vo.setPermissions(permissions);
            // 获取用户权限
            Set<String> menus = systemUserService.searchUserMenus(systemUserId);
            vo.setMenus(menus);
        }
        return GraceResult.ok(vo);
    }
}
