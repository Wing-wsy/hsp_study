package com.yz.model.condition.mis;

import lombok.Data;

@Data
public class TSystemMenuConditions {

    private Long fatherId;

    private String menuCode;

    private String language;

    private int minSort;

    private int maxSort;

    private Long id;

    private String menuName;

    private Integer level;

    private Integer sort;

    private Integer status;

    public static TSystemMenuConditions newInstance() {
        return new TSystemMenuConditions();
    }

    public TSystemMenuConditions addFatherId(Long fatherId) {
        this.fatherId = fatherId;
        return this;
    }

    public TSystemMenuConditions addSort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public TSystemMenuConditions addStatus(Integer status) {
        this.status = status;
        return this;
    }

    public TSystemMenuConditions addLevel(Integer level) {
        this.level = level;
        return this;
    }

    public TSystemMenuConditions addMenuCode(String menuCode) {
        this.menuCode = menuCode;
        return this;
    }

    public TSystemMenuConditions addMenuName(String menuName) {
        this.menuName = menuName;
        return this;
    }

    public TSystemMenuConditions addLanguage(String language) {
        this.language = language;
        return this;
    }

    public TSystemMenuConditions addMinSort(int minSort) {
        this.minSort = minSort;
        return this;
    }

    public TSystemMenuConditions addMaxSort(int maxSort) {
        this.maxSort = maxSort;
        return this;
    }

    public TSystemMenuConditions addId(Long id) {
        this.id = id;
        return this;
    }
}
