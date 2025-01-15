package com.yz.mis.bff.service;

import com.yz.model.from.mis_bff.SearchUserBriefInfoFrom;
import com.yz.model.res.mis_bff.SearchUserBriefInfoRes;

public interface CustomerService {

    public SearchUserBriefInfoRes searchUserBriefInfo(SearchUserBriefInfoFrom from);
}
