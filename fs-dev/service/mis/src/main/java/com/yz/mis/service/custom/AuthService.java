package com.yz.mis.service.custom;

import com.yz.model.dto.mis.LoginDTO;

/**
 * 测试
 */
public interface AuthService {

    /**
     * 登录接口
     * @param loginDTO
     * @return 系统用户ID(登录成功返回，否则返回null)
     */
    public Long login(LoginDTO loginDTO);

}
