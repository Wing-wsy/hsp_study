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

    public static TSystemMenuConditions newInstance() {
        return new TSystemMenuConditions();
    }

    public TSystemMenuConditions addFatherId(Long fatherId) {
        this.fatherId = fatherId;
        return this;
    }

    public TSystemMenuConditions addMenuCode(String menuCode) {
        this.menuCode = menuCode;
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
