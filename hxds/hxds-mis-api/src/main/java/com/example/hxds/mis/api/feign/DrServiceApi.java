package com.example.hxds.mis.api.feign;

import com.example.hxds.common.util.R;
//import com.example.hxds.mis.api.controller.form.SearchDriverBriefInfoForm;
import com.example.hxds.mis.api.controller.form.SearchDriverByPageForm;
import com.example.hxds.mis.api.controller.form.SearchDriverRealSummaryForm;
import com.example.hxds.mis.api.controller.form.UpdateDriverRealAuthForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-dr")
public interface DrServiceApi {

    @PostMapping("/driver/searchDriverByPage")
    public R searchDriverByPage(SearchDriverByPageForm form);

    @PostMapping("/driver/searchDriverRealSummary")
    public R searchDriverRealSummary(SearchDriverRealSummaryForm form);

    @PostMapping("/driver/updateDriverRealAuth")
    public R updateDriverRealAuth(UpdateDriverRealAuthForm form);

//    @PostMapping("/driver/searchDriverBriefInfo")
//    public R searchDriverBriefInfo(SearchDriverBriefInfoForm form);
    
}
