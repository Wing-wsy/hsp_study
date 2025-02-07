package com.yz.mis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yz.common.constant.Basic;
import com.yz.common.constant.Strings;
import com.yz.common.exception.GraceException;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.common.util.BeanUtils;
import com.yz.common.util.CollUtils;
import com.yz.common.util.ListUtils;
import com.yz.common.util.ObjectUtils;
import com.yz.common.util.StrUtils;
import com.yz.common.util.page.PageResult;
import com.yz.mis.mapper.TSystemDeptMapper;
import com.yz.mis.service.TSystemDeptService;
import com.yz.model.bo.mis.AddDeptBO;
import com.yz.model.condition.mis.TSystemDeptConditions;
import com.yz.model.entity.TSystemDept;
import com.yz.model.entity.TSystemMenu;
import com.yz.model.entity.TSystemRole;
import com.yz.model.vo.mis.SelectDeptListVO;
import com.yz.model.vo.mis.SelectRoleListVO;
import com.yz.service.base.service.BaseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class TSystemDeptServiceImpl extends BaseService implements TSystemDeptService {

    @Resource
    private TSystemDeptMapper tSystemDeptMapper;

    @Override
    public PageResult<SelectDeptListVO> selectDeptList(String language, Integer status, Integer page, Integer pageSize) {
        return selectTSystemDeptListPage(language, status, page, pageSize);
    }

    @Override
    public void addDept(AddDeptBO bo) {
        // 存在则不能重复添加
        boolean existRecords = isExistRecords(bo.getDeptCode());
        if (existRecords) {
            GraceException.display(ResponseStatusEnum.DEPT_INSERT_ERROR);
        }

        List<String> languages = ListUtils.getLanguageList();
        for (String language : languages) {
            String deptName = null;
            if (Strings.LOCALE_ES.equals(language)) {
                deptName = bo.getDeptNameByES();
            } else {
                deptName = bo.getDeptNameByZH();
            }

            // 获取子级菜单最大序号
            int maxSort = getDeptMaxSort(bo.getParentId(), language);
            insertDept(deptName, bo.getDeptCode(),
                    bo.getParentId(), bo.getMobile(),
                    bo.getEmail(), bo.getComment(),
                    language,maxSort + 1,
                    Basic.NORMAL);
        }
    }

    @Override
    public void deleteDept(Long id) {
        TSystemDept tSystemDept = tSystemDeptMapper.selectById(id);
        if (ObjectUtils.isNull(tSystemDept)) {
            GraceException.display(ResponseStatusEnum.DEPT_DELETE_ERROR);
        }

        List<String> languages = ListUtils.getLanguageList();
        for (String language : languages) {
            // 根据 code和language 查询 id
            TSystemDeptConditions conditions1 = TSystemDeptConditions.newInstance()
                    .addDeptCode(tSystemDept.getDeptCode())
                    .addLanguage(language);
            TSystemDept tSystemDept1 = selectTSystemDept(conditions1).get(0);

            // 修改其他部门排序
            TSystemDeptConditions conditions2 = TSystemDeptConditions.newInstance()
                    .addLanguage(language)
                    .addMinSort(tSystemDept1.getSort() + 1);
            List<TSystemDept> tSystemDepts1 = selectTSystemDept(conditions2);
            for (TSystemDept systemDept : tSystemDepts1) {
                systemDept.setSort(systemDept.getSort() - 1);
                tSystemDeptMapper.updateById(systemDept);
            }

            // 删除当前部门
            tSystemDept1.setIsDelete(Basic.DELETE);
            tSystemDeptMapper.updateById(tSystemDept1);
        }
    }

    private List<TSystemDept> selectTSystemDept(TSystemDeptConditions conditions) {
        QueryWrapper<TSystemDept> selectWrapper = new QueryWrapper<>();
        selectWrapper.eq("is_delete", Basic.VAILD);

        if (StrUtils.isNotBlank(conditions.getLanguage()))
            selectWrapper.eq("language", conditions.getLanguage());

        if (ObjectUtils.isNotNull(conditions.getParentId()))
            selectWrapper.eq("parent_id", conditions.getParentId());

        if (StrUtils.isNotBlank(conditions.getDeptCode()))
            selectWrapper.eq("dept_code", conditions.getDeptCode());

        // 大于等于
        if (conditions.getMinSort() > 0)
            selectWrapper.ge("sort", conditions.getMinSort());

        // 小于
        if (conditions.getMaxSort() > 0)
            selectWrapper.lt("sort", conditions.getMaxSort());

        // 不等于
        if (ObjectUtils.isNotNull(conditions.getId()))
            selectWrapper.ne("id", conditions.getId());

        selectWrapper.orderByAsc("sort");

        List<TSystemDept> tSystemDepts = tSystemDeptMapper.selectList(selectWrapper);
        return tSystemDepts;
    }

    // 分页
    private PageResult<SelectDeptListVO> selectTSystemDeptListPage(String language, Integer status, Integer page, Integer pageSize) {
        QueryWrapper<TSystemDept> selectWrapper = new QueryWrapper<>();

        if (StrUtils.isNotBlank(language)) {
            selectWrapper.eq("language", language);
        }

        if (ObjectUtils.isNotNull(status) && status == Basic.NORMAL)
            selectWrapper.eq("status", status);

        selectWrapper.orderByAsc("sort");

        // 设置分页参数
        Page<TSystemDept> pageInfo = new Page<>(page, pageSize);
        tSystemDeptMapper.selectPage(pageInfo,selectWrapper);

        List<TSystemDept> tSystemDepts = pageInfo.getRecords();
        List<SelectDeptListVO> dtoList = BeanUtils.convertBeanList(tSystemDepts, SelectDeptListVO.class);

        Page<SelectDeptListVO> selectDeptListVOPage = convertPage(pageInfo, dtoList);
        return setPagePlus(selectDeptListVOPage);
    }

    private boolean isExistRecords(String deptCode) {
        boolean flag = false;
        TSystemDeptConditions conditions1 = TSystemDeptConditions.newInstance()
                .addDeptCode(deptCode)
                .addLanguage(Strings.LOCALE_ES);
        List<TSystemDept> tSystemDepts1 = selectTSystemDept(conditions1);
        if (CollUtils.isNotEmpty(tSystemDepts1)) {
            flag = true;
        }

        TSystemDeptConditions conditions2 = TSystemDeptConditions.newInstance()
                .addDeptCode(deptCode)
                .addLanguage(Strings.LOCALE_ZH);
        List<TSystemDept> tSystemDepts2 = selectTSystemDept(conditions2);
        if (CollUtils.isNotEmpty(tSystemDepts2)) {
            flag = true;
        }
        return flag;
    }


    private void insertDept(String deptName, String deptCode,
                            Long parentId, String mobile,
                            String email, String comment,
                            String language,Integer sort,
                            Integer status) {
        LocalDateTime localDateTime = LocalDateTime.now();
        TSystemDept tSystemDept = new TSystemDept();
        tSystemDept.setDeptName(deptName);
        tSystemDept.setDeptCode(deptCode);
        if (parentId == null)
            parentId = 0L;

        tSystemDept.setParentId(parentId);
        tSystemDept.setMobile(mobile);
        tSystemDept.setEmail(email);
        tSystemDept.setComment(comment);
        tSystemDept.setLanguage(language);
        tSystemDept.setSort(sort);
        tSystemDept.setStatus(status);
        tSystemDept.setIsDelete(Basic.VAILD);

        tSystemDept.setCreateTime(localDateTime);
        tSystemDept.setUpdateTime(localDateTime);
        tSystemDeptMapper.insert(tSystemDept);
    }

    private int getDeptMaxSort(Long parentId, String language) {
        Integer maxSort = tSystemDeptMapper.getDeptMaxSort(language, parentId);
        return maxSort != null ? maxSort : 0;
    }

}
