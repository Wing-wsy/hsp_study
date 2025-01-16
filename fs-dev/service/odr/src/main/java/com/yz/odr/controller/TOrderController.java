package com.yz.odr.controller;

import cn.hutool.core.bean.BeanUtil;
import com.yz.api.controller.odr.TOrderControllerApi;
import com.yz.common.result.GraceResult;
import com.yz.model.bo.odr.SearchOrderBO;
import com.yz.model.dto.odr.SearchOrderDTO;
import com.yz.model.vo.odr.SearchOrderVO;
import com.yz.odr.service.TOrderService;
import com.yz.service.base.controller.ServiceBaseController;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制层2
 */
@Slf4j
@RestController
public class TOrderController extends ServiceBaseController implements TOrderControllerApi {

    @Resource
    private TOrderService tOrderService;

    public GraceResult searchOrder(@RequestBody @Valid SearchOrderBO bo){
        SearchOrderDTO searchOrderDTO = tOrderService.searchOrder(bo.getOrderId());
        SearchOrderVO vo = BeanUtil.toBean(searchOrderDTO,SearchOrderVO.class);
        return GraceResult.ok(vo);
    }
}
