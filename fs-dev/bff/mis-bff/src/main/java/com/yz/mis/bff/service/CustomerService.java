package com.yz.mis.bff.service;

import com.yz.model.form.mis_bff.SearchOrderAndUserBriefInfoFrom;
import com.yz.model.form.mis_bff.SearchUserBriefInfoFrom;
import com.yz.model.res.mis_bff.SearchOrderAndUserBriefInfoRes;
import com.yz.model.res.mis_bff.SearchUserBriefInfoRes;

public interface CustomerService {

    public SearchUserBriefInfoRes searchUserBriefInfo(SearchUserBriefInfoFrom from);

    public SearchOrderAndUserBriefInfoRes searchOrderAndUserBriefInfo(SearchOrderAndUserBriefInfoFrom from);
}
