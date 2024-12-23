package com.example.hxds.bff.driver.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.map.MapUtil;
import com.example.hxds.bff.driver.controller.form.ClearNewOrderQueueForm;
import com.example.hxds.bff.driver.controller.form.CreateDriverFaceModelForm;
import com.example.hxds.bff.driver.controller.form.LoginForm;
import com.example.hxds.bff.driver.controller.form.RegisterNewDriverForm;
import com.example.hxds.bff.driver.controller.form.RemoveLocationCacheForm;
import com.example.hxds.bff.driver.controller.form.SearchDriverAuthForm;
import com.example.hxds.bff.driver.controller.form.SearchDriverBaseInfoForm;
import com.example.hxds.bff.driver.controller.form.UpdateDriverAuthForm;
//import com.example.hxds.bff.driver.service.DriverLocationService;
import com.example.hxds.bff.driver.service.DriverService;
//import com.example.hxds.bff.driver.service.NewOrderMessageService;
import com.example.hxds.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/driver")
@Tag(name = "DriverController", description = "司机模块Web接口")
public class DriverController {
    @Resource
    private DriverService driverService;

//    @Resource
//    private DriverLocationService driverLocationService;
//
//    @Resource
//    private NewOrderMessageService newOrderMessageService;

    @PostMapping("/registerNewDriver")
    @Operation(summary = "新司机注册")
    public R registerNewDriver(@RequestBody @Valid RegisterNewDriverForm form){
        long driverId=driverService.registerNewDriver(form);
        // 会话登录：StpUtil.login() 参数填写要登录的账号id，建议的数据类型：long | int | String， 不可以传入复杂类型，如：User、Admin 等等
        StpUtil.login(driverId);
        // 获取当前会话的token
        String token=StpUtil.getTokenInfo().getTokenValue();
        return R.ok().put("token",token);
    }

    @PostMapping("/updateDriverAuth")
    @Operation(summary = "更新实名认证信息")
    @SaCheckLogin // 该注解要求用户必须登陆之后，才能访问该方法【前端会将令牌放在请求头】
    public R updateDriverAuth(@RequestBody @Valid UpdateDriverAuthForm form){
        long driverId=StpUtil.getLoginIdAsLong();
        form.setDriverId(driverId);
        int rows=driverService.updateDriverAuth(form);
        return R.ok().put("rows",rows);
    }
    @PostMapping("/createDriverFaceModel")
    @Operation(summary = "创建司机人脸模型归档")
    @SaCheckLogin
    public R createDriverFaceModel(@RequestBody @Valid CreateDriverFaceModelForm form){
        long driverId=StpUtil.getLoginIdAsLong();
        form.setDriverId(driverId);
        String result = driverService.createDriverFaceModel(form);
        return R.ok().put("result",result);
    }

    @PostMapping("/login")
    @Operation(summary = "登陆系统")
    public R login(@RequestBody @Valid LoginForm form){
        HashMap map=driverService.login(form);
        if(map!=null){
            long driverId= MapUtil.getLong(map,"id");
            byte realAuth=Byte.parseByte(MapUtil.getStr(map,"realAuth"));
            boolean archive=MapUtil.getBool(map,"archive");
            StpUtil.login(driverId);
            String token=StpUtil.getTokenInfo().getTokenValue();
            return R.ok().put("token",token).put("realAuth",realAuth).put("archive",archive);
        }
        return R.ok();
    }

//    @GetMapping("/logout")
//    @Operation(summary = "退出系统")
//    @SaCheckLogin
//    public R logout(){
//        StpUtil.logout();
//        return R.ok();
//    }
//
    @PostMapping("/searchDriverBaseInfo")
    @Operation(summary = "查询司机基本信息")
    @SaCheckLogin
    public R searchDriverBaseInfo(){
        long driverId=StpUtil.getLoginIdAsLong();
        SearchDriverBaseInfoForm form=new SearchDriverBaseInfoForm();
        form.setDriverId(driverId);
        HashMap map = driverService.searchDriverBaseInfo(form);
        return R.ok().put("result",map);
    }

    @PostMapping("/searchWorkbenchData")
    @Operation(summary = "查询司机工作台数据")
    @SaCheckLogin
    public R searchWorkbenchData(){
        long driverId = StpUtil.getLoginIdAsLong();
        HashMap result = driverService.searchWorkbenchData(driverId);
        return R.ok().put("result",result);
    }

    @GetMapping("/searchDriverAuth")
    @Operation(summary = "查询司机认证信息")
    @SaCheckLogin
    public R searchDriverAuth(){
        long driverId = StpUtil.getLoginIdAsLong();
        SearchDriverAuthForm form=new SearchDriverAuthForm();
        form.setDriverId(driverId);
        HashMap map = driverService.searchDriverAuth(form);
        return R.ok().put("result",map);
    }

//    @PostMapping("/startWork")
//    @Operation(summary = "开始接单")
//    @SaCheckLogin
//    public R startWork(){
//        long driverId = StpUtil.getLoginIdAsLong();
//        RemoveLocationCacheForm form_1=new RemoveLocationCacheForm();
//        form_1.setDriverId(driverId);
//        driverLocationService.removeLocationCache(form_1);
//
//        ClearNewOrderQueueForm form_2=new ClearNewOrderQueueForm();
//        form_2.setUserId(driverId);
//        newOrderMessageService.clearNewOrderQueue(form_2);
//
//        return R.ok();
//    }
//
//    @PostMapping("/stopWork")
//    @Operation(summary = "停止接单")
//    @SaCheckLogin
//    public R stopWork(){
//        long driverId = StpUtil.getLoginIdAsLong();
//        RemoveLocationCacheForm form_1=new RemoveLocationCacheForm();
//        form_1.setDriverId(driverId);
//        driverLocationService.removeLocationCache(form_1);
//
//        ClearNewOrderQueueForm form_2=new ClearNewOrderQueueForm();
//        form_2.setUserId(driverId);
//        newOrderMessageService.clearNewOrderQueue(form_2);
//
//        return R.ok();
//    }
}
