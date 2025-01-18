package com.yz.odr.service;

import com.yz.common.util.page.PageResult;
import com.yz.model.dto.odr.OrderDTO;

/**
 * 测试
 */
public interface TOrderService {

    public OrderDTO searchOrder(Long orderId);

    public PageResult<OrderDTO> searchOrderByUser(Long userId, Integer page, Integer pageSize);
}
