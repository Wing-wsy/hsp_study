package com.yz.mis.controller;

import com.yz.api.controller.mis.GeneralConfigControllerApi;
import com.yz.common.exception.GraceException;
import com.yz.common.result.GraceResult;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.common.util.ObjectUtils;
import com.yz.common.util.StrUtils;
import com.yz.common.util.page.PageResult;
import com.yz.mis.service.custom.GeneralConfigService;
import com.yz.mis.service.impl.custom.CacheServiceImpl;
import com.yz.model.bo.common.CommonLanguageBO;
import com.yz.model.bo.common.CommonPageBO;
import com.yz.model.bo.mis.DeleteResponseResultBO;
import com.yz.model.bo.mis.InsertResponseResultBO;
import com.yz.model.bo.mis.SelectResponseResultBO;
import com.yz.model.bo.mis.UpdateResponseResultBO;
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
    @Resource
    private CacheServiceImpl cacheServiceImpl;

    @Override
    public GraceResult searchResponseResult(@RequestBody @Valid SelectResponseResultBO bo) {

        PageResult<ResponseStatusEnum.ResponseStatusResult> responseStatusResults =
                generalConfigService.searchResponseResult(
                        bo.getCode(),bo.getMsg(),
                        bo.getLanguage(), bo.getPage(),
                        bo.getPageSize());
        return GraceResult.ok(responseStatusResults);

    }

    @Override
    public GraceResult insertResponseResult(@RequestBody @Valid InsertResponseResultBO bo) {
        generalConfigService.insertResponseResult(bo);
        // 刷新缓存
        cacheServiceImpl.refreshResponseResultAll();
        return GraceResult.ok();
    }

    @Override
    public GraceResult updateResponseResult(@RequestBody @Valid UpdateResponseResultBO bo) {
        // 只传了其中一个，则拦截，要么都传，要么都不传
        if (StrUtils.isNotBlank(bo.getMsgByES()) != StrUtils.isNotBlank(bo.getMsgByZH())) {
            GraceException.display(ResponseStatusEnum.SYSTEM_PARAMS_SETTINGS_ERROR);
        }
        generalConfigService.updateResponseResult(bo);
        // 刷新缓存
        cacheServiceImpl.refreshResponseResultAll();
        return GraceResult.ok();
    }

    @Override
    public GraceResult deleteResponseResult(@RequestBody @Valid DeleteResponseResultBO bo) {
        generalConfigService.deleteResponseResult(bo.getId());
        return GraceResult.ok();
    }

}
