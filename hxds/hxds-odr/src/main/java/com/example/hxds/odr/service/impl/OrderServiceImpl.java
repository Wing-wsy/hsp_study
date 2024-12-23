package com.example.hxds.odr.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.example.hxds.common.exception.HxdsException;
import com.example.hxds.common.util.PageUtils;
import com.example.hxds.odr.db.dao.OrderBillDao;
import com.example.hxds.odr.db.dao.OrderDao;
import com.example.hxds.odr.db.pojo.OrderBillEntity;
import com.example.hxds.odr.db.pojo.OrderEntity;
import com.example.hxds.odr.service.OrderService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;

//    @Resource
//    private OrderBillDao orderBillDao;
//
//    @Resource
//    private RedisTemplate redisTemplate;

    @Override
    public HashMap searchDriverTodayBusinessData(long driverId) {
        HashMap result = orderDao.searchDriverTodayBusinessData(driverId);
        String duration = MapUtil.getStr(result, "duration");
        if (duration == null) {
            duration = "0";
        }
        result.replace("duration", duration);

        String income = MapUtil.getStr(result, "income");
        if (income == null) {
            income = "0.00";
        }
        result.replace("income", income);
        return result;
    }

//    @Override
//    @Transactional
//    @LcnTransaction
//    public String insertOrder(OrderEntity orderEntity, OrderBillEntity orderBillEntity) {
//        int rows = orderDao.insert(orderEntity);
//        if (rows == 1) {
//            String id = orderDao.searchOrderIdByUUID(orderEntity.getUuid());
//            orderBillEntity.setOrderId(Long.parseLong(id));
//            rows = orderBillDao.insert(orderBillEntity);
//            if (rows == 1) {
//                redisTemplate.opsForValue().set("order#" + id, "none");
//                redisTemplate.expire("order#" + id, 1, TimeUnit.MINUTES);
//                return id;
//            } else {
//                throw new HxdsException("保存新订单费用失败");
//            }
//        } else {
//            throw new HxdsException("保存新订单失败");
//        }
//    }
//
//    @Override
//    @Transactional
//    @LcnTransaction
//    public String acceptNewOrder(long driverId, long orderId) {
//        if (!redisTemplate.hasKey("order#" + orderId)) {
//            return "抢单失败";
//        }
//        redisTemplate.execute(new SessionCallback() {
//            @Override
//            public Object execute(RedisOperations operations) throws DataAccessException {
//                operations.watch("order#" + orderId);
//                operations.multi();
//                operations.opsForValue().set("order#" + orderId, driverId);
//                return operations.exec();
//            }
//        });
//
//        redisTemplate.delete("order#" + orderId);
//        HashMap param = new HashMap() {{
//            put("driverId", driverId);
//            put("orderId", orderId);
//        }};
//        int rows = orderDao.acceptNewOrder(param);
//        if (rows != 1) {
//            throw new HxdsException("接单失败，无法更新订单记录");
//        }
//        return "接单成功";
//    }
//
//    @Override
//    public HashMap searchDriverExecuteOrder(Map param) {
//        HashMap map = orderDao.searchDriverExecuteOrder(param);
//        return map;
//    }
//
//    @Override
//    public Integer searchOrderStatus(Map param) {
//        Integer status = orderDao.searchOrderStatus(param);
//        if (status == null) {
////            throw new HxdsException("没有查询到数据，请核对查询条件");
//            status = 0;
//        }
//        return status;
//    }
//
//    @Override
//    @Transactional
//    @LcnTransaction
//    public String deleteUnAcceptOrder(Map param) {
//        long orderId = MapUtil.getLong(param, "orderId");
//        if (!redisTemplate.hasKey("order#" + orderId)) {
//            return "订单取消失败";
//        }
//        redisTemplate.execute(new SessionCallback() {
//            @Override
//            public Object execute(RedisOperations operations) throws DataAccessException {
//                operations.watch("order#" + orderId);
//                operations.multi();
//                operations.opsForValue().set("order#" + orderId, "none");
//                return operations.exec();
//            }
//        });
//
//        redisTemplate.delete("order#" + orderId);
//        int rows = orderDao.deleteUnAcceptOrder(param);
//        if (rows != 1) {
//            return "订单取消失败";
//        }
//        rows = orderBillDao.deleteUnAcceptOrderBill(orderId);
//        if (rows != 1) {
//            return "账单取消失败";
//        }
//        return "订单取消成功";
//    }
//
//    @Override
//    public HashMap searchDriverCurrentOrder(long driverId) {
//        HashMap map = orderDao.searchDriverCurrentOrder(driverId);
//        return map;
//    }
//
//    @Override
//    public HashMap hasCustomerCurrentOrder(long customerId) {
//        HashMap result = new HashMap();
//        HashMap map = orderDao.hasCustomerUnAcceptOrder(customerId);
//        result.put("hasCustomerUnAcceptOrder", map != null);
//        result.put("unAcceptOrder", map);
//        Long id = orderDao.hasCustomerUnFinishedOrder(customerId);
//        result.put("hasCustomerUnFinishedOrder", id != null);
//        result.put("unFinishedOrder", id);
//        return result;
//    }
//
//    @Override
//    public HashMap searchOrderForMoveById(Map param) {
//        HashMap map = orderDao.searchOrderForMoveById(param);
//        return map;
//    }
//
//
//    @Override
//    @Transactional
//    @LcnTransaction
//    public int arriveStartPlace(Map param) {
//        long orderId = MapUtil.getLong(param, "orderId");
//        redisTemplate.opsForValue().set("order_driver_arrivied#" + orderId, "1");
//        int rows = orderDao.updateOrderStatus(param);
//        if (rows != 1) {
//            throw new HxdsException("更新订单状态失败");
//        }
//        return rows;
//    }
//
//    @Override
//    public boolean confirmArriveStartPlace(long orderId) {
//        String key = "order_driver_arrivied#" + orderId;
//        if (redisTemplate.hasKey(key) && redisTemplate.opsForValue().get(key).toString().equals("1")) {
//            redisTemplate.opsForValue().set(key, "2");
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    @Transactional
//    @LcnTransaction
//    public int startDriving(Map param) {
//        long orderId = MapUtil.getLong(param, "orderId");
//        String key = "order_driver_arrivied#" + orderId;
////        if(redisTemplate.hasKey(key)&&redisTemplate.opsForValue().get(key).toString().equals("2")){
////            redisTemplate.delete(key);
//        int rows = orderDao.updateOrderStatus(param);
//        if (rows != 1) {
//            throw new HxdsException("更新订单状态失败");
//        }
//        return rows;
////        }
////        return 0;
//    }
//
//    @Override
//    @Transactional
//    @LcnTransaction
//    public int updateOrderStatus(Map param) {
//        int rows = orderDao.updateOrderStatus(param);
//        if (rows != 1) {
//            throw new HxdsException("更新取消订单记录失败");
//        }
//        return rows;
//    }
//
//    @Override
//    public ArrayList<HashMap> searchOrderStartLocationIn30Days() {
//        ArrayList<String> list = orderDao.searchOrderStartLocationIn30Days();
//        ArrayList<HashMap> result = new ArrayList<>();
//        list.forEach(location -> {
//            JSONObject json = JSONUtil.parseObj(location);
//            String latitude = json.getStr("latitude");
//            String longitude = json.getStr("longitude");
//            latitude = latitude.substring(0, latitude.length() - 4);
//            latitude += "0001";
//            longitude = longitude.substring(0, longitude.length() - 4);
//            longitude += "0001";
//            HashMap map = new HashMap();
//            map.put("latitude", latitude);
//            map.put("longitude", longitude);
//            result.add(map);
//        });
//        return result;
//    }
//
//
//    @Override
//    public PageUtils searchOrderByPage(Map param) {
//        long count = orderDao.searchOrderCount(param);
//        ArrayList<HashMap> list = null;
//        if (count == 0) {
//            list = new ArrayList<>();
//        } else {
//            list = orderDao.searchOrderByPage(param);
//        }
//        int start = MapUtil.getInt(param, "start");
//        int length = MapUtil.getInt(param, "length");
//        PageUtils pageUtils = new PageUtils(list, count, start, length);
//        return pageUtils;
//    }
//
//    @Override
//    public HashMap searchOrderContent(long orderId) {
//        HashMap map = orderDao.searchOrderContent(orderId);
//        JSONObject startPlaceLocation = JSONUtil.parseObj(MapUtil.getStr(map, "startPlaceLocation"));
//        JSONObject endPlaceLocation = JSONUtil.parseObj(MapUtil.getStr(map, "endPlaceLocation"));
//        map.replace("startPlaceLocation", startPlaceLocation);
//        map.replace("endPlaceLocation", endPlaceLocation);
//        return map;
//    }
//
//    @Override
//    public boolean validDriverOwnOrder(Map param) {
//        long count = orderDao.validDriverOwnOrder(param);
//        return count == 1 ? true : false;
//    }
//
//    @Override
//    public HashMap searchSettlementNeedData(long orderId) {
//        HashMap map = orderDao.searchSettlementNeedData(orderId);
//        return map;
//    }
//
//    @Override
//    public HashMap searchOrderById(Map param) {
//        HashMap map = orderDao.searchOrderById(param);
//        String startPlaceLocation=MapUtil.getStr(map,"startPlaceLocation");
//        String endPlaceLocation=MapUtil.getStr(map,"endPlaceLocation");
//        map.replace("startPlaceLocation",JSONUtil.parseObj(startPlaceLocation));
//        map.replace("endPlaceLocation",JSONUtil.parseObj(endPlaceLocation));
//        return map;
//    }


}
