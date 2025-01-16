package com.yz.odr.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yz.common.exception.GraceException;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.common.util.BeanUtils;
import com.yz.model.dto.odr.OrderDTO;
import com.yz.model.entity.TOrder;
import com.yz.odr.mapper.TOrderMapper;
import com.yz.odr.service.TOrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 测试
 */
@Service
public class TOrderServiceImpl implements TOrderService {
    @Resource
    private TOrderMapper tOrderMapper;

    @Override
    public OrderDTO searchOrder(Long orderId) {
        TOrder tOrder = tOrderMapper.selectById(orderId);
        if (tOrder == null) {
            GraceException.display(ResponseStatusEnum.ORDER_NOT_FIND);
        }
        OrderDTO dto = BeanUtil.toBean(tOrder, OrderDTO.class);
        return dto;
    }

    @Override
    public List<OrderDTO> searchOrderByUser(Long userId) {
        QueryWrapper<TOrder> selectWrapper = new QueryWrapper<>();
        selectWrapper.eq("user_id", userId);
        List<TOrder> tOrders = tOrderMapper.selectList(selectWrapper);
        List<OrderDTO> dtoList = BeanUtils.convertBeanList(tOrders, OrderDTO.class);
        return dtoList;
    }
}
