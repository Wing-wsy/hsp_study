package com.yz.mis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yz.common.constant.Basic;
import com.yz.common.constant.Strings;
import com.yz.common.exception.GraceException;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.common.util.CollUtils;
import com.yz.common.util.ListUtils;
import com.yz.common.util.ObjectUtils;
import com.yz.common.util.StrUtils;
import com.yz.mis.mapper.TSystemDeptMapper;
import com.yz.mis.service.TSystemDeptService;
import com.yz.model.bo.mis.AddDeptBO;
import com.yz.model.entity.TSystemDept;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class TSystemDeptServiceImpl implements TSystemDeptService {

    @Resource
    private TSystemDeptMapper tSystemDeptMapper;

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
            if (Strings.LOCALE_ES_LOWER.equals(language)) {
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


    private List<TSystemDept> selectTSystemDept(Long parentId, String deptCode,
                                                String language, int minSort,
                                                int maxSort, Long id) {
        QueryWrapper<TSystemDept> selectWrapper = new QueryWrapper<>();
        selectWrapper.eq("is_delete", Basic.VAILD);

        if (StrUtils.isNotBlank(language))
            selectWrapper.eq("language", language);

        if (ObjectUtils.isNotNull(parentId))
            selectWrapper.eq("parent_id", parentId);

        if (StrUtils.isNotBlank(deptCode))
            selectWrapper.eq("dept_code", deptCode);

        // 大于等于
        if (minSort > 0)
            selectWrapper.ge("sort", minSort);

        // 小于
        if (maxSort > 0)
            selectWrapper.lt("sort", maxSort);

        // 不等于
        if (ObjectUtils.isNotNull(id))
            selectWrapper.ne("id", id);

        selectWrapper.orderByAsc("sort");

        List<TSystemDept> tSystemDepts = tSystemDeptMapper.selectList(selectWrapper);
        return tSystemDepts;
    }

    private boolean isExistRecords(String deptCode) {
        boolean flag = false;
        List<TSystemDept> tSystemDepts1 = selectTSystemDept(null, deptCode, Strings.LOCALE_ES_LOWER, 0, 0, null);
        if (CollUtils.isNotEmpty(tSystemDepts1)) {
            flag = true;
        }
        List<TSystemDept> tSystemDepts2 = selectTSystemDept(null, deptCode, Strings.LOCALE_ZH, 0, 0, null);
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
