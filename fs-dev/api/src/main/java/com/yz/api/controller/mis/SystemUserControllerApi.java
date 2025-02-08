package com.yz.api.controller.mis;


import com.yz.common.result.GraceResult;
import com.yz.model.bo.mis.InsertRoleBO;
import com.yz.model.bo.mis.InsertUserBO;
import com.yz.model.bo.mis.SelectUserListBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统用户控制层
 */
@Tag(name = "SystemUserController", description = "系统用户接口")
@RequestMapping("/user")
public interface SystemUserControllerApi {

    @PostMapping("/selectUserList")
    @Operation(summary = "查询用户列表【分页】")
    public GraceResult selectUserList(SelectUserListBO bo);

    @PostMapping("/insertUser")
    @Operation(summary = "新增用户")
    public GraceResult insertUser(InsertUserBO bo);

}
