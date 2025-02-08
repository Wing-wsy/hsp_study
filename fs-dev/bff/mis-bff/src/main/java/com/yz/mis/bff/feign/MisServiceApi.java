package com.yz.mis.bff.feign;

import com.yz.common.result.GraceResult;
import com.yz.model.bo.mis.AddMenuBO;
import com.yz.model.bo.mis.DeleteMenuBO;
import com.yz.model.bo.mis.MenuTreeBO;
import com.yz.model.bo.mis.UpdateMenuBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "mis-service", path = "/mis")
public interface MisServiceApi {

    @PostMapping("/menu/menuTree")
    public GraceResult menuTree(MenuTreeBO bo);

    @PostMapping("/menu/addMenu")
    public GraceResult addMenu(AddMenuBO bo);

    @DeleteMapping("/menu/deleteMenu")
    public GraceResult deleteMenu(DeleteMenuBO bo);

    @PutMapping("/menu/updateMenu")
    public GraceResult updateMenu(UpdateMenuBO bo);
}

