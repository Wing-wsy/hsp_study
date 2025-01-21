package com.yz.mis.controller;

import com.yz.api.controller.cst.GeneralConfigControllerApi;
import com.yz.common.result.GraceResult;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.mis.service.GeneralConfigService;
import com.yz.model.bo.common.CommonLanguageBO;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class GeneralConfigController implements GeneralConfigControllerApi {

    @Resource
    private GeneralConfigService generalConfigService;

    @Override
    public GraceResult searchResponseResult(@RequestBody @Valid CommonLanguageBO bo) {

        List<ResponseStatusEnum.ResponseStatusResult> responseStatusResults =
                generalConfigService.searchResponseResult(bo.getLanguage());

        return GraceResult.ok(responseStatusResults);

    }
}
