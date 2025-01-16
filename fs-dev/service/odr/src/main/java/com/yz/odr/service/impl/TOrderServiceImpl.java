package com.yz.odr.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.yz.common.exception.GraceException;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.model.dto.odr.SearchOrderDTO;
import com.yz.model.entity.TOrder;
import com.yz.odr.mapper.TOrderMapper;
import com.yz.odr.service.TOrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 测试
 */
@Service
public class TOrderServiceImpl implements TOrderService {
    @Resource
    private TOrderMapper tOrderMapper;

    @Override
    public SearchOrderDTO searchOrder(Long orderId) {
        TOrder tOrder = tOrderMapper.selectById(orderId);
        if (tOrder == null) {
            GraceException.display(ResponseStatusEnum.ORDER_NOT_FIND);
        }
        SearchOrderDTO dto = BeanUtil.toBean(tOrder,SearchOrderDTO.class);
        return dto;
    }
}
