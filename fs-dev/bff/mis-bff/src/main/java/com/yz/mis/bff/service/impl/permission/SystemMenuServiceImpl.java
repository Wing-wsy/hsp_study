package com.yz.mis.bff.service.impl.permission;

import com.yz.common.exception.GraceException;
import com.yz.common.result.GraceResult;
import com.yz.common.util.BeanUtils;
import com.yz.mis.bff.feign.MisServiceApi;
import com.yz.mis.bff.service.permission.SystemMenuService;
import com.yz.model.bo.mis.AddMenuBO;
import com.yz.model.bo.mis.DeleteMenuBO;
import com.yz.model.bo.mis.MenuTreeBO;
import com.yz.model.bo.mis.UpdateMenuBO;
import com.yz.model.form.mis_bff.permission.AddMenuForm;
import com.yz.model.form.mis_bff.permission.DeleteMenuForm;
import com.yz.model.form.mis_bff.permission.MenuTreeForm;
import com.yz.model.form.mis_bff.permission.UpdateMenuForm;
import com.yz.model.res.mis_bff.permission.MenuTreeRes;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;


@Service
public class SystemMenuServiceImpl implements SystemMenuService {

    @Resource
    private MisServiceApi misServiceApi;

    @Override
    public MenuTreeRes menuTree(MenuTreeForm form) {
        MenuTreeBO bo  = BeanUtils.toBean(form, MenuTreeBO.class);
        GraceResult result = misServiceApi.menuTree(bo);

        if (!result.getSuccess()) {
            GraceException.displayCustom(result.getCode());
        }
        // 1. 查询数据
        ArrayList<HashMap> map = (ArrayList)result.getData();

        // 2.组装数据
        MenuTreeRes res = new MenuTreeRes();
        res.setMap(map);
        return res;
    }

    @Override
    public void addMenu(AddMenuForm form) {
        AddMenuBO bo  = BeanUtils.toBean(form, AddMenuBO.class);
        GraceResult result = misServiceApi.addMenu(bo);
        if (!result.getSuccess()) {
            GraceException.displayCustom(result.getCode());
        }
    }

    @Override
    public void deleteMenu(DeleteMenuForm form) {
        DeleteMenuBO bo  = BeanUtils.toBean(form, DeleteMenuBO.class);
        GraceResult result = misServiceApi.deleteMenu(bo);
        if (!result.getSuccess()) {
            GraceException.displayCustom(result.getCode());
        }
    }

    @Override
    public void updateMenu(UpdateMenuForm form) {
        UpdateMenuBO bo  = BeanUtils.toBean(form, UpdateMenuBO.class);
        GraceResult result = misServiceApi.updateMenu(bo);
        if (!result.getSuccess()) {
            GraceException.displayCustom(result.getCode());
        }
    }

}
