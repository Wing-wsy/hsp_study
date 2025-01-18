package com.yz.mis.service.impl;

import com.yz.mis.service.AuthService;
import com.yz.model.dto.mis.LoginDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public boolean login(LoginDTO loginDTO) {

        return true;
    }
}
