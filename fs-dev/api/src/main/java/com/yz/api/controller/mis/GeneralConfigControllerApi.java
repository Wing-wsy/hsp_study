package com.yz.api.controller.mis;

import com.yz.common.result.GraceResult;
import com.yz.model.bo.mis.DeleteResponseResultBO;
import com.yz.model.bo.mis.InsertResponseResultBO;
import com.yz.model.bo.mis.SelectResponseResultBO;
import com.yz.model.bo.mis.UpdateResponseResultBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 通用配置控制器
 */
@Tag(name = "GeneralConfigController", description = "通用配置控制器")
@RequestMapping("/general-config")
public interface GeneralConfigControllerApi {

    @PostMapping("/searchResponseResult")
    @Operation(summary = "查询系统响应结果类型")
    public GraceResult searchResponseResult(SelectResponseResultBO bo);

    @PostMapping("/insertResponseResult")
    @Operation(summary = "新增系统响应结果类型")
    public GraceResult insertResponseResult(InsertResponseResultBO bo);

    @PostMapping("/updateResponseResult")
    @Operation(summary = "修改系统响应结果类型")
    public GraceResult updateResponseResult(UpdateResponseResultBO bo);

    @PostMapping("/deleteResponseResult")
    @Operation(summary = "删除系统响应结果类型")
    public GraceResult deleteResponseResult(DeleteResponseResultBO bo);

}
