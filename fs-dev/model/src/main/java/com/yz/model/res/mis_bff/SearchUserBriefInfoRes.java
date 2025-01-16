package com.yz.model.res.mis_bff;

import com.yz.model.vo.cst.SearchUserBriefInfoVO;
import com.yz.model.vo.odr.SearchOrderVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "查询用户基础信息Res")
public class SearchUserBriefInfoRes {

    @Schema(description = "用户信息")
    private SearchUserBriefInfoVO searchUserBriefInfoVO;

    @Schema(description = "订单信息")
    private SearchOrderVO searchOrderVO;

}
