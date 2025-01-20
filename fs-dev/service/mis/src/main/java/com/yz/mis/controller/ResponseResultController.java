package com.yz.mis.controller;

import com.yz.api.controller.cst.ResponseResultControllerApi;
import com.yz.common.result.GraceResult;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.model.bo.common.CommonLanguageBO;
import io.swagger.v3.oas.annotations.headers.Header;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试控制层2
 */
@Slf4j
@RestController
public class ResponseResultController implements ResponseResultControllerApi {


    @Override
    public GraceResult searchResponseResult(@RequestBody @Valid CommonLanguageBO bo) {
        List<ResponseStatusEnum.ResponseStatusResult> allResponseStatus
                = ResponseStatusEnum.getAllResponseStatus();

        return GraceResult.ok(allResponseStatus);
    }
}
