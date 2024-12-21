package com.example.hxds.bff.driver.service;

import com.example.hxds.bff.driver.controller.form.CreateDriverFaceModelForm;
import com.example.hxds.bff.driver.controller.form.LoginForm;
import com.example.hxds.bff.driver.controller.form.RegisterNewDriverForm;
import com.example.hxds.bff.driver.controller.form.SearchDriverAuthForm;
import com.example.hxds.bff.driver.controller.form.SearchDriverBaseInfoForm;
import com.example.hxds.bff.driver.controller.form.UpdateDriverAuthForm;

import java.util.HashMap;

public interface DriverService {
    public long registerNewDriver(RegisterNewDriverForm form);

    public int updateDriverAuth(UpdateDriverAuthForm form);

    public String createDriverFaceModel(CreateDriverFaceModelForm form);

    public HashMap login(LoginForm form);

    public HashMap searchDriverBaseInfo(SearchDriverBaseInfoForm form);

//    public HashMap searchWorkbenchData(long driverId);
//
//    public HashMap searchDriverAuth(SearchDriverAuthForm form);


}
