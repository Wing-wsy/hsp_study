package com.yz.api.controller.mis;


import com.yz.common.result.GraceResult;
import com.yz.model.bo.mis.AddDeptBO;
import com.yz.model.bo.mis.DeleteDeptBO;
import com.yz.model.bo.mis.SelectDeptListBO;
import com.yz.model.bo.mis.UpdateDeptBO;
import com.yz.model.bo.mis.UpdatePermBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @DeleteMapping("/deleteDept")
    @Operation(summary = "删除部门")
    public GraceResult deleteDept(DeleteDeptBO bo);

    @PostMapping("/selectDeptList")
    @Operation(summary = "查询部门列表(分页)")
    public GraceResult selectDeptList(SelectDeptListBO bo);

    @PutMapping("/updateDept")
    @Operation(summary = "修改部门")
    public GraceResult updateDept(UpdateDeptBO bo);

}
