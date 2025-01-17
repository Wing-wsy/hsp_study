package com.yz.odr.controller;

import com.yz.api.controller.odr.TOrderControllerApi;
import com.yz.common.result.GraceResult;
import com.yz.common.util.BeanUtils;
import com.yz.common.util.page.PageResult;
import com.yz.model.bo.odr.SearchOrderByOrderIdBO;
import com.yz.model.bo.odr.SearchOrderByUserBO;
import com.yz.model.dto.odr.OrderDTO;
import com.yz.model.vo.odr.OrderVO;
import com.yz.model.vo.odr.OrderListVO;
import com.yz.odr.service.TOrderService;
import com.yz.service.base.controller.BaseController;
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
public class TOrderController extends BaseController implements TOrderControllerApi {

    @Resource
    private TOrderService tOrderService;

    public GraceResult searchOrderByOrderId(@RequestBody @Valid SearchOrderByOrderIdBO bo){
        OrderDTO orderDTO = tOrderService.searchOrder(bo.getOrderId());
        OrderVO vo = BeanUtils.toBean(orderDTO, OrderVO.class);
        return GraceResult.ok(vo);
    }

    @Override
    public GraceResult searchOrderByUser(@RequestBody @Valid SearchOrderByUserBO bo) {
        List<OrderVO> orderVOList = new ArrayList<>();
        PageResult<OrderDTO> pageResultOrderDTO = tOrderService.searchOrderByUser(bo.getUserId(), bo.getPage(), bo.getPageSize());
        List<OrderDTO> rows = pageResultOrderDTO.getRows();
        for (OrderDTO orderDTO : rows) {
            OrderVO vo = BeanUtils.toBean(orderDTO, OrderVO.class);
            orderVOList.add(vo);
        }
        PageResult<OrderVO> pageResultOrderVO = convertPageResult(pageResultOrderDTO,orderVOList);
        return GraceResult.ok(pageResultOrderVO);
    }
}
