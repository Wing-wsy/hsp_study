package com.yz.model.res.mis_bff;

import com.yz.model.vo.cst.UserBriefInfoVO;
import com.yz.model.vo.odr.OrderVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "查询用户基础信息Res")
public class SearchUserBriefInfoRes {

    @Schema(description = "用户信息")
    private UserBriefInfoVO userBriefInfoVO;

    @Schema(description = "订单信息")
    private OrderVO orderVO;

}
