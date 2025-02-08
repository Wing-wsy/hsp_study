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
import com.yz.model.bo.mis.UpdateDeptBO;
import com.yz.model.condition.mis.TSystemDeptConditions;
import com.yz.model.entity.TSystemDept;
import com.yz.model.vo.mis.SelectDeptListVO;
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
    public PageResult<SelectDeptListVO> selectDeptList(TSystemDeptConditions conditions) {
        return selectTSystemDeptListPage(conditions);
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
            TSystemDeptConditions conditions = TSystemDeptConditions.newInstance()
                    .addDeptName(deptName)
                    .addDeptCode(bo.getDeptCode())
                    .addParentId(bo.getParentId())
                    .addMobile(bo.getMobile())
                    .addEmail(bo.getEmail())
                    .addComment(bo.getComment())
                    .addLanguage(language)
                    .addSort(maxSort + 1)
                    .addStatus(Basic.NORMAL);
            insertDeptByConditions(conditions);
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
            TSystemDept tSystemDept1 = selectTSystemDeptList(conditions1).get(0);

            // 修改其他部门排序
            TSystemDeptConditions conditions2 = TSystemDeptConditions.newInstance()
                    .addLanguage(language)
                    .addMinSort(tSystemDept1.getSort() + 1);
            List<TSystemDept> tSystemDepts1 = selectTSystemDeptList(conditions2);
            for (TSystemDept systemDept : tSystemDepts1) {
                systemDept.setSort(systemDept.getSort() - 1);
                tSystemDeptMapper.updateById(systemDept);
            }

            // 删除当前部门
            tSystemDept1.setIsDelete(Basic.DELETE);
            tSystemDeptMapper.updateById(tSystemDept1);
        }
    }

    @Override
    public void updateDept(UpdateDeptBO bo) {
        TSystemDept tSystemDept = tSystemDeptMapper.selectById(bo.getId());
        if (ObjectUtils.isNull(tSystemDept)) {
            GraceException.display(ResponseStatusEnum.DEPT_UPDATE_NOT_FIND_ERROR);
        }

        List<String> languages = ListUtils.getLanguageList();
        for (String language : languages) {
            TSystemDeptConditions conditions1 = TSystemDeptConditions.newInstance()
                    .addDeptCode(tSystemDept.getDeptCode())
                    .addLanguage(language);
            TSystemDept tSystemDept1 = selectTSystemDeptList(conditions1).get(0);

            // 1. 判断是否需要修改基础信息
            if (StrUtils.isNotBlank(bo.getDeptNameByES())) {
                // 1.1 修改后的值不能重复
                if (StrUtils.isNotBlank(bo.getDeptCode())) {
                    boolean existRecords = isExistRecords(bo.getDeptCode());
                    if (existRecords) {
                        GraceException.display(ResponseStatusEnum.DEPT_UPDATE_ERROR);
                    }
                }

                String deptName = null;
                if (Strings.LOCALE_ES.equals(language)) {
                    deptName = bo.getDeptNameByES();
                } else {
                    deptName = bo.getDeptNameByZH();
                }

                // 1.2 修改权限
                TSystemDeptConditions conditions = TSystemDeptConditions.newInstance()
                        .addDeptName(deptName)
                        .addDeptCode(bo.getDeptCode())
                        .addMobile(bo.getMobile())
                        .addEmail(bo.getEmail())
                        .addComment(bo.getComment());
                updateDeptByConditions(tSystemDept1, conditions);
            } else {
                // 2. 只是修改状态、排序
                Integer currentSort = null;
                // 2.1 上移
                if (StrUtils.isNotBlank(bo.getMoveMode()) && Basic.UP.equals(bo.getMoveMode())) {
                    if (tSystemDept1.getSort() == Basic.ONE_INT)
                        GraceException.display(ResponseStatusEnum.DEPT_MOVE_UP_ERROR);

                    currentSort = tSystemDept1.getSort() - 1;
                    // 找到上一个菜单,并将序号+1
                    TSystemDeptConditions conditions2 = TSystemDeptConditions.newInstance()
                            .addLanguage(language)
                            .addMinSort(currentSort);
                    TSystemDept tSystemDept2 = selectTSystemDeptList(conditions2).get(0);
                    tSystemDept2.setSort(tSystemDept2.getSort() + 1);
                    tSystemDeptMapper.updateById(tSystemDept2);
                }

                // 2.2 下移
                if (StrUtils.isNotBlank(bo.getMoveMode()) && Basic.DOWN.equals(bo.getMoveMode())) {
                    int maxSort = getDeptMaxSort(null, language);
                    if (tSystemDept1.getSort() == maxSort)
                        GraceException.display(ResponseStatusEnum.DEPT_MOVE_DOWN_ERROR);

                    currentSort = tSystemDept1.getSort() + 1;
                    // 找到下一个菜单,并将序号-1
                    TSystemDeptConditions conditions3 = TSystemDeptConditions.newInstance()
                            .addLanguage(language)
                            .addMinSort(currentSort);
                    TSystemDept tSystemDept2 = selectTSystemDeptList(conditions3).get(0);
                    tSystemDept2.setSort(tSystemDept2.getSort() - 1);
                    tSystemDeptMapper.updateById(tSystemDept2);
                }

                // 修改权限序号
                if (ObjectUtils.isNotNull(currentSort)) {
                    tSystemDept1.setSort(currentSort);
                }

                // 修改权限状态
                if (ObjectUtils.isNotNull(bo.getStatus())) {
                    tSystemDept1.setStatus(bo.getStatus());
                }
                tSystemDeptMapper.updateById(tSystemDept1);
            }
        }
    }

    private List<TSystemDept> selectTSystemDeptList(TSystemDeptConditions conditions) {
        QueryWrapper<TSystemDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", Basic.VAILD);

        if (StrUtils.isNotBlank(conditions.getLanguage()))
            queryWrapper.eq("language", conditions.getLanguage());

        if (ObjectUtils.isNotNull(conditions.getParentId()))
            queryWrapper.eq("parent_id", conditions.getParentId());

        if (StrUtils.isNotBlank(conditions.getDeptCode()))
            queryWrapper.eq("dept_code", conditions.getDeptCode());

        // 大于等于
        if (conditions.getMinSort() > 0)
            queryWrapper.ge("sort", conditions.getMinSort());

        // 小于
        if (conditions.getMaxSort() > 0)
            queryWrapper.lt("sort", conditions.getMaxSort());

        // 不等于
        if (ObjectUtils.isNotNull(conditions.getId()))
            queryWrapper.ne("id", conditions.getId());

        queryWrapper.orderByAsc("sort");

        List<TSystemDept> tSystemDepts = tSystemDeptMapper.selectList(queryWrapper);
        return tSystemDepts;
    }

    // 分页
    private PageResult<SelectDeptListVO> selectTSystemDeptListPage(TSystemDeptConditions conditions) {
        QueryWrapper<TSystemDept> queryWrapper = new QueryWrapper<>();

        if (StrUtils.isNotBlank(conditions.getLanguage())) {
            queryWrapper.eq("language", conditions.getLanguage());
        }

        if (ObjectUtils.isNotNull(conditions.getStatus()) && conditions.getStatus() == Basic.NORMAL)
            queryWrapper.eq("status", conditions.getStatus());

        queryWrapper.orderByAsc("sort");

        // 设置分页参数
        Page<TSystemDept> pageInfo = new Page<>(conditions.getPage(), conditions.getPageSize());
        tSystemDeptMapper.selectPage(pageInfo,queryWrapper);

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
        List<TSystemDept> tSystemDepts1 = selectTSystemDeptList(conditions1);
        if (CollUtils.isNotEmpty(tSystemDepts1)) {
            flag = true;
        }

        TSystemDeptConditions conditions2 = TSystemDeptConditions.newInstance()
                .addDeptCode(deptCode)
                .addLanguage(Strings.LOCALE_ZH);
        List<TSystemDept> tSystemDepts2 = selectTSystemDeptList(conditions2);
        if (CollUtils.isNotEmpty(tSystemDepts2)) {
            flag = true;
        }
        return flag;
    }


    private void insertDeptByConditions(TSystemDeptConditions conditions) {
        LocalDateTime localDateTime = LocalDateTime.now();
        TSystemDept tSystemDept = new TSystemDept();
        tSystemDept.setDeptName(conditions.getDeptName());
        tSystemDept.setDeptCode(conditions.getDeptCode());
        tSystemDept.setParentId(0L);
        tSystemDept.setMobile(conditions.getMobile());
        tSystemDept.setEmail(conditions.getEmail());
        tSystemDept.setComment(conditions.getComment());
        tSystemDept.setLanguage(conditions.getLanguage());
        tSystemDept.setSort(conditions.getSort());
        tSystemDept.setStatus(conditions.getStatus());
        tSystemDept.setIsDelete(Basic.VAILD);

        tSystemDept.setCreateTime(localDateTime);
        tSystemDept.setUpdateTime(localDateTime);
        tSystemDeptMapper.insert(tSystemDept);
    }

    private int getDeptMaxSort(Long parentId, String language) {
        Integer maxSort = tSystemDeptMapper.getDeptMaxSort(language, parentId);
        return maxSort != null ? maxSort : 0;
    }

    private void updateDeptByConditions(TSystemDept tSystemDept, TSystemDeptConditions conditions) {

        if (StrUtils.isNotBlank(conditions.getDeptName()))
            tSystemDept.setDeptName(conditions.getDeptName());

        if (StrUtils.isNotBlank(conditions.getDeptCode()))
            tSystemDept.setDeptCode(conditions.getDeptCode());

        if (StrUtils.isNotBlank(conditions.getMobile()))
            tSystemDept.setMobile(conditions.getMobile());

        if (StrUtils.isNotBlank(conditions.getEmail()))
            tSystemDept.setEmail(conditions.getEmail());

        if (StrUtils.isNotBlank(conditions.getComment()))
            tSystemDept.setComment(conditions.getComment());

        if (ObjectUtils.isNotNull(conditions.getStatus()))
            tSystemDept.setStatus(conditions.getStatus());

        if (ObjectUtils.isNotNull(conditions.getSort()))
            tSystemDept.setSort(conditions.getSort());

        tSystemDeptMapper.updateById(tSystemDept);
    }

}
