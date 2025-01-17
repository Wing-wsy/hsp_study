package com.yz.odr.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yz.common.exception.GraceException;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.common.util.BeanUtils;
import com.yz.common.util.page.PageResult;
import com.yz.model.dto.odr.OrderDTO;
import com.yz.model.entity.TOrder;
import com.yz.odr.mapper.TOrderMapper;
import com.yz.odr.service.TOrderService;
import com.yz.service.base.service.BaseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 测试
 */
@Service
public class TOrderServiceImpl extends BaseService implements TOrderService {
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
    public PageResult<OrderDTO> searchOrderByUser(Long userId, Integer page, Integer pageSize) {
        QueryWrapper<TOrder> selectWrapper = new QueryWrapper<>();
        selectWrapper.eq("user_id", userId);
        // 设置分页参数
        Page<TOrder> pageInfoTOrder = new Page<>(page, pageSize);
        tOrderMapper.selectPage(pageInfoTOrder,selectWrapper);

        List<TOrder> tOrders = pageInfoTOrder.getRecords();
        List<OrderDTO> dtoList = BeanUtils.convertBeanList(tOrders, OrderDTO.class);

        Page<OrderDTO> pageInfoOrderDTO = convertPage(pageInfoTOrder, dtoList);
        return setPagePlus(pageInfoOrderDTO);
    }
}
