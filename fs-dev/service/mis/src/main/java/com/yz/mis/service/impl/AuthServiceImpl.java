package com.yz.mis.service.impl;

import com.yz.mis.service.AuthService;
import com.yz.model.dto.mis.LoginDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public Long login(LoginDTO loginDTO) {
        // TODO 登录验证通过后，返回用户ID
        return 667318461394980864L;
    }
}
