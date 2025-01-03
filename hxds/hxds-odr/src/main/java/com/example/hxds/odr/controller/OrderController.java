package com.example.hxds.odr.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import com.example.hxds.common.util.PageUtils;
import com.example.hxds.common.util.R;
import com.example.hxds.odr.controller.form.AcceptNewOrderForm;
import com.example.hxds.odr.controller.form.ArriveStartPlaceForm;
import com.example.hxds.odr.controller.form.ConfirmArriveStartPlaceForm;
import com.example.hxds.odr.controller.form.DeleteUnAcceptOrderForm;
import com.example.hxds.odr.controller.form.HasCustomerCurrentOrderForm;
import com.example.hxds.odr.controller.form.InsertOrderForm;
import com.example.hxds.odr.controller.form.SearchDriverCurrentOrderForm;
import com.example.hxds.odr.controller.form.SearchDriverExecuteOrderForm;
import com.example.hxds.odr.controller.form.SearchDriverTodayBusinessDataForm;
import com.example.hxds.odr.controller.form.SearchOrderByIdForm;
import com.example.hxds.odr.controller.form.SearchOrderByPageForm;
import com.example.hxds.odr.controller.form.SearchOrderContentForm;
import com.example.hxds.odr.controller.form.SearchOrderForMoveByIdForm;
import com.example.hxds.odr.controller.form.SearchOrderStatusForm;
import com.example.hxds.odr.controller.form.SearchSettlementNeedDataForm;
import com.example.hxds.odr.controller.form.StartDrivingForm;
import com.example.hxds.odr.controller.form.UpdateOrderStatusForm;
import com.example.hxds.odr.controller.form.ValidDriverOwnOrderForm;
import com.example.hxds.odr.db.pojo.OrderBillEntity;
import com.example.hxds.odr.db.pojo.OrderEntity;
import com.example.hxds.odr.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Tag(name = "OrderController", description = "订单模块Web接口")
public class OrderController {
    @Resource
    private OrderService orderService;

    @PostMapping("/searchDriverTodayBusinessData")
    @Operation(summary = "查询司机当天营业数据")
    public R searchDriverTodayBusinessData(@RequestBody @Valid SearchDriverTodayBusinessDataForm form) {
        HashMap result = orderService.searchDriverTodayBusinessData(form.getDriverId());
        return R.ok().put("result", result);
    }

    @PostMapping("/insertOrder")
    @Operation(summary = "顾客下单")
    public R insertOrder(@RequestBody @Valid InsertOrderForm form) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUuid(form.getUuid());
        orderEntity.setCustomerId(form.getCustomerId());
        orderEntity.setStartPlace(form.getStartPlace());
        JSONObject json = new JSONObject();
        json.set("latitude", form.getStartPlaceLatitude());
        json.set("longitude", form.getStartPlaceLongitude());
        orderEntity.setStartPlaceLocation(json.toString());
        orderEntity.setEndPlace(form.getEndPlace());
        json = new JSONObject();
        json.set("latitude", form.getEndPlaceLatitude());
        json.set("longitude", form.getEndPlaceLongitude());
        orderEntity.setEndPlaceLocation(json.toString());
        orderEntity.setExpectsMileage(new BigDecimal(form.getExpectsMileage()));
        orderEntity.setExpectsFee(new BigDecimal(form.getExpectsFee()));
        orderEntity.setFavourFee(new BigDecimal(form.getFavourFee()));
        orderEntity.setChargeRuleId(form.getChargeRuleId());
        orderEntity.setCarPlate(form.getCarPlate());
        orderEntity.setCarType(form.getCarType());
        orderEntity.setDate(form.getDate());

        OrderBillEntity orderBillEntity = new OrderBillEntity();
        orderBillEntity.setBaseMileage(form.getBaseMileage());
        orderBillEntity.setBaseMileagePrice(new BigDecimal(form.getBaseMileagePrice()));
        orderBillEntity.setExceedMileagePrice(new BigDecimal(form.getExceedMileagePrice()));
        orderBillEntity.setBaseMinute(form.getBaseMinute());
        orderBillEntity.setExceedMinutePrice(new BigDecimal(form.getExceedMinutePrice()));
        orderBillEntity.setBaseReturnMileage(form.getBaseReturnMileage());
        orderBillEntity.setExceedReturnPrice(new BigDecimal(form.getExceedReturnPrice()));

        String id = orderService.insertOrder(orderEntity, orderBillEntity);
        return R.ok().put("result", id);
    }

    @PostMapping("/acceptNewOrder")
    @Operation(summary = "司机接单")
    public R acceptNewOrder(@RequestBody @Valid AcceptNewOrderForm form) {
        String result = orderService.acceptNewOrder(form.getDriverId(), form.getOrderId());
        return R.ok().put("result", result);
    }

    @PostMapping("/searchDriverExecuteOrder")
    @Operation(summary = "查询司机正在执行的订单记录")
    public R searchDriverExecuteOrder(@RequestBody @Valid SearchDriverExecuteOrderForm form) {
        Map param = BeanUtil.beanToMap(form);
        HashMap map = orderService.searchDriverExecuteOrder(param);
        return R.ok().put("result", map);
    }

    @PostMapping("/searchOrderStatus")
    @Operation(summary = "查询订单状态")
    public R searchOrderStatus(@RequestBody @Valid SearchOrderStatusForm form) {
        Map param = BeanUtil.beanToMap(form);
        Integer status = orderService.searchOrderStatus(param);
        return R.ok().put("result", status);
    }

    @PostMapping("/deleteUnAcceptOrder")
    @Operation(summary = "删除没有司机接单的订单")
    public R deleteUnAcceptOrder(@RequestBody @Valid DeleteUnAcceptOrderForm form) {
        Map param = BeanUtil.beanToMap(form);
        String result = orderService.deleteUnAcceptOrder(param);
        return R.ok().put("result", result);
    }

    @PostMapping("/searchDriverCurrentOrder")
    @Operation(summary = "查询司机当前订单")
    public R searchDriverCurrentOrder(@RequestBody @Valid SearchDriverCurrentOrderForm form) {
        HashMap map = orderService.searchDriverCurrentOrder(form.getDriverId());
        return R.ok().put("result", map);
    }

    @PostMapping("/hasCustomerCurrentOrder")
    @Operation(summary = "查询乘客是否存在当前的订单")
    public R hasCustomerCurrentOrder(@RequestBody @Valid HasCustomerCurrentOrderForm form) {
        HashMap map = orderService.hasCustomerCurrentOrder(form.getCustomerId());
        return R.ok().put("result", map);
    }

    @PostMapping("/searchOrderForMoveById")
    @Operation(summary = "查询订单信息用于司乘同显功能")
    public R searchOrderForMoveById(@RequestBody @Valid SearchOrderForMoveByIdForm form) {
        Map param = BeanUtil.beanToMap(form);
        HashMap map = orderService.searchOrderForMoveById(param);
        return R.ok().put("result", map);
    }

    @PostMapping("/arriveStartPlace")
    @Operation(summary = "司机到达上车点")
    public R arriveStartPlace(@RequestBody @Valid ArriveStartPlaceForm form) {
        Map param = BeanUtil.beanToMap(form);
        param.put("status", 3);
        int rows = orderService.arriveStartPlace(param);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/confirmArriveStartPlace")
    @Operation(summary = "乘客确认司机到达上车点")
    public R confirmArriveStartPlace(@RequestBody @Valid ConfirmArriveStartPlaceForm form) {
        boolean result = orderService.confirmArriveStartPlace(form.getOrderId());
        return R.ok().put("result", result);
    }

    @PostMapping("/startDriving")
    @Operation(summary = "开始代驾")
    public R startDriving(@RequestBody @Valid StartDrivingForm form) {
        Map param = BeanUtil.beanToMap(form);
        param.put("status", 4);
        int rows = orderService.startDriving(param);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/updateOrderStatus")
    @Operation(summary = "更新订单状态")
    public R updateOrderStatus(@RequestBody @Valid UpdateOrderStatusForm form) {
        Map param = BeanUtil.beanToMap(form);
        int rows = orderService.updateOrderStatus(param);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/searchOrderByPage")
    @Operation(summary = "查询订单分页记录")
    public R searchOrderByPage(@RequestBody @Valid SearchOrderByPageForm form) {
        Map param = BeanUtil.beanToMap(form);
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1) * length;
        param.put("start", start);
        PageUtils pageUtils = orderService.searchOrderByPage(param);
        return R.ok().put("result", pageUtils);
    }

    @PostMapping("/searchOrderContent")
    @Operation(summary = "查询订单详情")
    public R searchOrderContent(@RequestBody @Valid SearchOrderContentForm form) {
        HashMap map = orderService.searchOrderContent(form.getOrderId());
        return R.ok().put("result", map);
    }

    @PostMapping("/searchOrderStartLocationIn30Days")
    @Operation(summary = "查询30天以内订单上车点定位")
    public R searchOrderStartLocationIn30Days() {
        ArrayList<HashMap> result = orderService.searchOrderStartLocationIn30Days();
        return R.ok().put("result", result);
    }

    @PostMapping("/validDriverOwnOrder")
    @Operation(summary = "查询司机是否关联某订单")
    public R validDriverOwnOrder(@RequestBody @Valid ValidDriverOwnOrderForm form) {
        Map param = BeanUtil.beanToMap(form);
        boolean bool = orderService.validDriverOwnOrder(param);
        return R.ok().put("result", bool);
    }

    @PostMapping("/searchSettlementNeedData")
    @Operation(summary = "查询订单的开始和等时")
    public R searchSettlementNeedData(@RequestBody @Valid SearchSettlementNeedDataForm form) {
        HashMap map = orderService.searchSettlementNeedData(form.getOrderId());
        return R.ok().put("result", map);
    }

    @PostMapping("/searchOrderById")
    @Operation(summary = "根据ID查询订单信息")
    public R searchOrderById(@RequestBody @Valid SearchOrderByIdForm form){
        Map param = BeanUtil.beanToMap(form);
        HashMap map = orderService.searchOrderById(param);
        return R.ok().put("result", map);
    }

}
