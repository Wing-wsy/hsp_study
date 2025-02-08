package com.yz.mis.bff.service.permission;

import com.yz.model.form.mis_bff.permission.AddMenuForm;
import com.yz.model.form.mis_bff.permission.DeleteMenuForm;
import com.yz.model.form.mis_bff.permission.MenuTreeForm;
import com.yz.model.form.mis_bff.permission.UpdateMenuForm;
import com.yz.model.res.mis_bff.permission.MenuTreeRes;

public interface SystemMenuService {

    /**
     * 查询菜单树
     */
    public MenuTreeRes menuTree(MenuTreeForm form);

    /**
     * 新增菜单
     */
    public void addMenu(AddMenuForm form);

    /**
     * 删除菜单
     */
    public void deleteMenu(DeleteMenuForm form);

    /**
     * 修改菜单
     */
    public void updateMenu(UpdateMenuForm form);


}
