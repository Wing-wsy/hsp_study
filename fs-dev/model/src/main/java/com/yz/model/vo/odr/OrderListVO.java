package com.yz.model.vo.odr;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "查询用户订单信息VO")
public class OrderListVO {

    private List<OrderVO> orderVOList;
}
