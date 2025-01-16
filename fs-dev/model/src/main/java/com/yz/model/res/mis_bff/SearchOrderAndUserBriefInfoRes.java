package com.yz.model.res.mis_bff;

import com.yz.model.vo.cst.UserBriefInfoVO;
import com.yz.model.vo.odr.OrderVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "查询用户基础信息和订单信息Res")
public class SearchOrderAndUserBriefInfoRes {

    @Schema(description = "用户信息")
    private UserBriefInfoVO userBriefInfoVO;

    private List<OrderVO> orderVOList;

}
