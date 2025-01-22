package com.yz.mis.controller;

import com.yz.api.controller.cst.CacheControllerApi;
import com.yz.common.result.GraceResult;
import com.yz.mis.service.CacheService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CacheController implements CacheControllerApi {
    @Resource
    private CacheService cacheService;

    @Override
    public GraceResult refreshResponseResult() {
        cacheService.refreshResponseResultAll();
        return GraceResult.ok();
    }
}
