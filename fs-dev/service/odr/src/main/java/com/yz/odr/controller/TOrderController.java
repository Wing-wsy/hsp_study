package com.yz.odr.controller;

import com.yz.api.controller.odr.TOrderControllerApi;
import com.yz.common.result.GraceResult;
import com.yz.common.util.BeanUtils;
import com.yz.model.bo.odr.SearchOrderByOrderIdBO;
import com.yz.model.bo.odr.SearchOrderByUserBO;
import com.yz.model.dto.odr.OrderDTO;
import com.yz.model.vo.odr.OrderVO;
import com.yz.model.vo.odr.OrderListVO;
import com.yz.odr.service.TOrderService;
import com.yz.service.base.controller.ServiceBaseController;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试控制层2
 */
@Slf4j
@RestController
public class TOrderController extends ServiceBaseController implements TOrderControllerApi {

    @Resource
    private TOrderService tOrderService;

    public GraceResult searchOrderByOrderId(@RequestBody @Valid SearchOrderByOrderIdBO bo){
        OrderDTO orderDTO = tOrderService.searchOrder(bo.getOrderId());
        OrderVO vo = BeanUtils.toBean(orderDTO, OrderVO.class);
        return GraceResult.ok(vo);
    }

    @Override
    public GraceResult searchOrderByUser(@RequestBody @Valid SearchOrderByUserBO bo) {
        OrderListVO orderListVO = new OrderListVO();
        List<OrderVO> orderVOList = new ArrayList<>();
        List<OrderDTO> orderDTOList = tOrderService.searchOrderByUser(bo.getUserId());
        for (OrderDTO orderDTO : orderDTOList) {
            OrderVO vo = BeanUtils.toBean(orderDTO, OrderVO.class);
            orderVOList.add(vo);
        }
        orderListVO.setOrderVOList(orderVOList);
        return GraceResult.ok(orderListVO);
    }
}
