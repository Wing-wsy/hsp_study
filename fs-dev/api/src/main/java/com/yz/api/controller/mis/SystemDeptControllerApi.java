package com.yz.api.controller.mis;


import com.yz.common.result.GraceResult;
import com.yz.model.bo.mis.AddDeptBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统部门控制层
 */
@Tag(name = "SystemDeptController", description = "系统部门接口")
@RequestMapping("/dept")
public interface SystemDeptControllerApi {

    @PostMapping("/addDept")
    @Operation(summary = "新增部门")
    public GraceResult addDept(AddDeptBO bo);

}
