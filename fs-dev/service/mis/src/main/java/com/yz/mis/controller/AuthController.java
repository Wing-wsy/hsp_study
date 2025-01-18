package com.yz.mis.controller;


import com.yz.api.controller.mis.AuthControllerApi;
import com.yz.common.result.GraceResult;
import com.yz.common.util.BeanUtils;
import com.yz.mis.service.AuthService;
import com.yz.model.bo.mis.SystemLoginBO;
import com.yz.model.dto.mis.LoginDTO;
import com.yz.model.vo.mis.SystemLoginVO;
import com.yz.service.base.controller.BaseController;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController extends BaseController implements AuthControllerApi {

    @Resource
    private AuthService authService;

    @Override
    public GraceResult login(@RequestBody @Valid SystemLoginBO bo) {
        LoginDTO loginDTO = BeanUtils.toBean(bo, LoginDTO.class);
        boolean status = authService.login(loginDTO);
        SystemLoginVO vo = new SystemLoginVO();
        vo.setLoginStatus(status);
        return GraceResult.ok(vo);
    }
}
