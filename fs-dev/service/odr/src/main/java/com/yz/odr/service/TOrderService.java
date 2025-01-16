package com.yz.odr.service;

import com.yz.model.dto.odr.OrderDTO;

import java.util.List;

/**
 * 测试
 */
public interface TOrderService {

    public OrderDTO searchOrder(Long orderId);

    public List<OrderDTO> searchOrderByUser(Long userId);
}
